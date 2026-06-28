package com.manohar.nova.service;

import reactor.core.publisher.Flux;

/**
 * Service interface for streaming AI token responses.
 */
public interface StreamingAIService {

    /**
     * Generates a token-by-token stream response from the AI model.
     *
     * @param message the user message or prompt
     * @return a Flux emitting chunks of the generated response
     */
    Flux<String> generateStream(String message);
}
