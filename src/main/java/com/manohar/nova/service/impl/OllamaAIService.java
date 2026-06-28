package com.manohar.nova.service.impl;

import com.manohar.nova.memory.MemoryService;
import com.manohar.nova.prompt.PromptBuilderService;
import com.manohar.nova.service.AIService;
import com.manohar.nova.service.StreamingAIService;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Ollama implementation of the AIService and StreamingAIService interfaces.
 */
@Service
@ConditionalOnProperty(name = "nova.ai.provider", havingValue = "ollama", matchIfMissing = true)
public class OllamaAIService implements AIService, StreamingAIService {

    private final OllamaChatModel chatModel;
    private final MemoryService memoryService;
    private final PromptBuilderService promptBuilderService;

    /**
     * Constructor injection for dependency injection.
     *
     * @param chatModel            the autowired OllamaChatModel driver
     * @param memoryService        the memory service instance
     * @param promptBuilderService the prompt builder service instance
     */
    public OllamaAIService(OllamaChatModel chatModel,
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
            
            System.out.println("=== Constructed Prompt (Ollama) ===");
            System.out.println(structuredPrompt);
            System.out.println("====================================");
            
            String response = chatModel.call(structuredPrompt);
            if (response == null) {
                return "Nova cannot connect to Ollama. Please start Ollama and try again.";
            }
            return response;
        } catch (Exception e) {
            // Catch connection or model missing errors gracefully
            return "Nova cannot connect to Ollama. Please start Ollama and try again.";
        }
    }

    @Override
    public Flux<String> generateStream(String message) {
        try {
            String structuredPrompt = promptBuilderService.buildPrompt(message, memoryService.getConversation());
            
            System.out.println("=== Constructed Prompt (Ollama Stream) ===");
            System.out.println(structuredPrompt);
            System.out.println("==========================================");

            return chatModel.stream(structuredPrompt)
                    .doOnError(Throwable::printStackTrace)
                    .onErrorReturn("Nova cannot connect to Ollama. Please start Ollama and try again.");
        } catch (Exception e) {
            return Flux.just("Nova cannot connect to Ollama. Please start Ollama and try again.");
        }
    }
}
