package com.manohar.nova.controller;

import com.manohar.nova.dto.ChatRequest;
import com.manohar.nova.intent.Intent;
import com.manohar.nova.intent.IntentService;
import com.manohar.nova.intent.IntentType;
import com.manohar.nova.memory.MemoryService;
import com.manohar.nova.service.StreamingAIService;
import com.manohar.nova.tools.ToolDispatcher;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * REST controller for streaming chat responses using Server-Sent Events (SSE).
 */
@RestController
@RequestMapping("/api/chat/stream")
public class ChatStreamController {

    private final IntentService intentService;
    private final ToolDispatcher toolDispatcher;
    private final StreamingAIService streamingAIService;
    private final MemoryService memoryService;

    /**
     * Constructor injection for dependency injection.
     *
     * @param intentService        the intent service instance
     * @param toolDispatcher       the tool dispatcher instance
     * @param streamingAIService   the streaming AI service instance
     * @param memoryService        the memory service instance
     */
    public ChatStreamController(IntentService intentService,
                                ToolDispatcher toolDispatcher,
                                StreamingAIService streamingAIService,
                                MemoryService memoryService) {
        this.intentService = intentService;
        this.toolDispatcher = toolDispatcher;
        this.streamingAIService = streamingAIService;
        this.memoryService = memoryService;
    }

    /**
     * Exposes a POST endpoint returning Server-Sent Events containing response tokens.
     *
     * @param request the validated chat request
     * @return a Flux of text chunks
     */
    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@Valid @RequestBody ChatRequest request) {
        String message = request.getMessage();
        memoryService.saveUserMessage(message);

        Intent intent = intentService.detectIntent(message);

        if (intent.getType() == IntentType.TOOL) {
            String replyText = toolDispatcher.execute(intent.getToolName(), intent.getOriginalMessage());
            memoryService.saveAssistantMessage(replyText);
            return Flux.just(replyText);
        } else {
            StringBuilder completeResponse = new StringBuilder();
            return streamingAIService.generateStream(intent.getOriginalMessage())
                    .doOnNext(completeResponse::append)
                    .doOnComplete(() -> {
                        String fullText = completeResponse.toString();
                        if (!fullText.isEmpty()) {
                            memoryService.saveAssistantMessage(fullText);
                        }
                    })
                    .doOnCancel(() -> {
                        String fullText = completeResponse.toString();
                        if (!fullText.isEmpty()) {
                            memoryService.saveAssistantMessage(fullText);
                        }
                    });
        }
    }
}
