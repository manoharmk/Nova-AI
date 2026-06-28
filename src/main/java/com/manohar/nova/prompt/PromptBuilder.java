package com.manohar.nova.prompt;

import com.manohar.nova.memory.ConversationMessage;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementation of {@link PromptBuilderService} that constructs structured prompts with history boundaries.
 */
@Service
public class PromptBuilder implements PromptBuilderService {

    private static final String SYSTEM_PROMPT = "You are Nova, a helpful desktop AI assistant.";
    private static final int MAX_HISTORY_MESSAGES = 10;

    @Override
    public String buildPrompt(String currentMessage, List<ConversationMessage> history) {
        StringBuilder builder = new StringBuilder();

        // System Prompt block
        builder.append("System:\n\n\"")
               .append(SYSTEM_PROMPT)
               .append("\"\n\n");

        // History block
        if (history != null && !history.isEmpty()) {
            int historySize = history.size();
            
            // Check if the current user message is already stored at the end of the history list
            boolean lastIsCurrent = history.get(historySize - 1).getRole().equals("user")
                    && history.get(historySize - 1).getContent().equals(currentMessage);
            
            int limitCount = lastIsCurrent ? historySize - 1 : historySize;
            
            if (limitCount > 0) {
                builder.append("Conversation:\n\n");
                
                int startIndex = Math.max(0, limitCount - MAX_HISTORY_MESSAGES);
                List<ConversationMessage> subHistory = history.subList(startIndex, limitCount);

                for (ConversationMessage msg : subHistory) {
                    String role = msg.getRole();
                    String capitalizedRole = role.substring(0, 1).toUpperCase() + role.substring(1);
                    builder.append(capitalizedRole)
                           .append(":\n")
                           .append(msg.getContent())
                           .append("\n\n");
                }
            }
        }

        // Current User query block
        builder.append("Current User:\n\n")
               .append(currentMessage)
               .append("\n");

        return builder.toString();
    }
}
