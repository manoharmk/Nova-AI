package com.manohar.nova.service;

/**
 * Interface defining the contract for AI/LLM service interactions.
 */
public interface AIService {

    /**
     * Generates a text response from the underlying AI model for a given user message.
     *
     * @param message the message to send to the AI model
     * @return the generated reply text from the AI model
     */
    String generate(String message);
}
