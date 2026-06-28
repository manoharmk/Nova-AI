package com.manohar.nova.service.impl;

import com.manohar.nova.memory.MemoryService;
import com.manohar.nova.prompt.PromptBuilderService;
import com.manohar.nova.service.AIService;
import com.manohar.nova.service.StreamingAIService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * OpenAI implementation of the AIService and StreamingAIService interfaces.
 */
@Service
public class OpenAIAIService implements AIService, StreamingAIService {

    private final ChatModel chatModel;
    private final MemoryService memoryService;
    private final PromptBuilderService promptBuilderService;

    /**
     * Constructor injection for dependency injection.
     *
     * @param chatModel            the autowired ChatModel driver
     * @param memoryService        the memory service instance
     * @param promptBuilderService the prompt builder service instance
     */
    public OpenAIAIService(ChatModel chatModel,
                           MemoryService memoryService,
                           PromptBuilderService promptBuilderService) {
        this.chatModel = chatModel;
        this.memoryService = memoryService;
        this.promptBuilderService = promptBuilderService;
    }

    @Override
    public String generate(String message) {
        try {
            String structuredPrompt = promptBuilderService.buildPrompt(message, memoryService.getConversation());
            
            System.out.println("=== Constructed Prompt ===");
            System.out.println(structuredPrompt);
            System.out.println("==========================");
            
            String response = chatModel.call(structuredPrompt);
            if (response == null) {
                return "Nova is currently unavailable. Please try again in a moment.";
            }
            return response;
        } catch (Exception e) {
            // Do not expose stack traces or API details to the user
            return "Nova is currently unavailable. Please try again in a moment.";
        }
    }

    @Override
    public Flux<String> generateStream(String message) {
        try {
            String structuredPrompt = promptBuilderService.buildPrompt(message, memoryService.getConversation());
            
            System.out.println("=== Constructed Prompt (Stream) ===");
            System.out.println(structuredPrompt);
            System.out.println("===================================");

            return chatModel.stream(structuredPrompt)
                    .onErrorReturn("Nova is currently unavailable. Please try again in a moment.");
        } catch (Exception e) {
            // Do not expose stack traces or API details to the user
            return Flux.just("Nova is currently unavailable. Please try again in a moment.");
        }
    }
}
