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
        boolean matchesTime = TIME_KEYWORDS.stream().anyMatch(lowerMessage::contains);

        if (matchesTime) {
            return new Intent(IntentType.TOOL, "time", message);
        }

        return new Intent(IntentType.CHAT, null, message);
    }
}
