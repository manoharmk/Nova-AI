package com.manohar.nova.intent.impl;

import com.manohar.nova.intent.Intent;
import com.manohar.nova.intent.IntentService;
import com.manohar.nova.intent.IntentType;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Basic implementation of {@link IntentService} using simple keyword detection.
 */
@Service
public class SimpleIntentService implements IntentService {

    private static final List<String> TIME_KEYWORDS = List.of(
            "time",
            "clock",
            "date",
            "current time",
            "today"
    );

    @Override
    public Intent detectIntent(String message) {
        if (message == null) {
            return new Intent(IntentType.CHAT, null, "");
        }

        String lowerMessage = message.toLowerCase();

        if (TIME_KEYWORDS.stream().anyMatch(lowerMessage::contains)) {
            return new Intent(IntentType.TOOL, "time", message);
        }
        if (lowerMessage.contains("calculate") || lowerMessage.contains("calculator")) {
            return new Intent(IntentType.TOOL, "calculator", message);
        }
        if (lowerMessage.contains("system")) {
            return new Intent(IntentType.TOOL, "system", message);
        }
        if (lowerMessage.contains("uuid")) {
            return new Intent(IntentType.TOOL, "uuid", message);
        }
        if (lowerMessage.contains("random")) {
            return new Intent(IntentType.TOOL, "random", message);
        }
        if (lowerMessage.contains("echo")) {
            return new Intent(IntentType.TOOL, "echo", message);
        }

        return new Intent(IntentType.CHAT, null, message);
    }
}
