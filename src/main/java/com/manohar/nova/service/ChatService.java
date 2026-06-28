package com.manohar.nova.service;

import com.manohar.nova.dto.ChatRequest;
import com.manohar.nova.dto.ChatResponse;
import com.manohar.nova.intent.Intent;
import com.manohar.nova.intent.IntentService;
import com.manohar.nova.intent.IntentType;
import com.manohar.nova.tools.ToolDispatcher;
import org.springframework.stereotype.Service;

/**
 * Service class handling business logic for the Nova AI chat.
 */
@Service
public class ChatService {

    private final AIService aiService;
    private final IntentService intentService;
    private final ToolDispatcher toolDispatcher;

    /**
     * Constructor injection for dependency injection.
     *
     * @param aiService      the AI service implementation
     * @param intentService  the intent detection service
     * @param toolDispatcher the tool dispatcher coordinator
     */
    public ChatService(AIService aiService, IntentService intentService, ToolDispatcher toolDispatcher) {
        this.aiService = aiService;
        this.intentService = intentService;
        this.toolDispatcher = toolDispatcher;
    }

    /**
     * Processes a chat request, routes intent, and returns the response.
     *
     * @param request the chat request containing the user's message
     * @return the chat response containing the reply
     */
    public ChatResponse chat(ChatRequest request) {
        String message = request.getMessage();
        Intent intent = intentService.detectIntent(message);

        String replyText;
        if (intent.getType() == IntentType.TOOL) {
            replyText = toolDispatcher.execute(intent.getToolName(), intent.getOriginalMessage());
        } else {
            replyText = aiService.generate(intent.getOriginalMessage());
        }

        return new ChatResponse(replyText);
    }
}
