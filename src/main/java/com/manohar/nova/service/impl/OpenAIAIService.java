package com.manohar.nova.service.impl;

import com.manohar.nova.service.AIService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

/**
 * OpenAI implementation of the AIService interface.
 */
@Service
public class OpenAIAIService implements AIService {

    private final ChatModel chatModel;

    /**
     * Constructor injection for dependency injection.
     *
     * @param chatModel the autowired ChatModel driver
     */
    public OpenAIAIService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String generate(String message) {
        try {
            String response = chatModel.call(message);
            if (response == null) {
                return "Nova is currently unavailable. Please try again in a moment.";
            }
            return response;
        } catch (Exception e) {
            // Do not expose stack traces or API details to the user
            return "Nova is currently unavailable. Please try again in a moment.";
        }
    }
}
