package com.manohar.nova.prompt;

import com.manohar.nova.memory.ConversationMessage;
import java.util.List;

/**
 * Service interface for building structured prompts including conversation history.
 */
public interface PromptBuilderService {

    /**
     * Constructs a structured prompt from the current message and the conversation history.
     *
     * @param currentMessage the current user message
     * @param history        the existing list of conversation messages
     * @return the formatted prompt string
     */
    String buildPrompt(String currentMessage, List<ConversationMessage> history);
}
