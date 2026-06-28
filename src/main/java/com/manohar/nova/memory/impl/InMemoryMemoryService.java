package com.manohar.nova.memory.impl;

import com.manohar.nova.memory.ConversationMessage;
import com.manohar.nova.memory.MemoryService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Thread-safe, in-memory implementation of {@link MemoryService} which limits history size to 20 messages.
 */
@Service
public class InMemoryMemoryService implements MemoryService {

    private final List<ConversationMessage> conversation = Collections.synchronizedList(new ArrayList<>());
    private static final int MAX_HISTORY = 20;

    @Override
    public void saveUserMessage(String message) {
        addMessage(new ConversationMessage("user", message));
    }

    @Override
    public void saveAssistantMessage(String message) {
        addMessage(new ConversationMessage("assistant", message));
    }

    @Override
    public List<ConversationMessage> getConversation() {
        synchronized (conversation) {
            return new ArrayList<>(conversation);
        }
    }

    @Override
    public void clear() {
        conversation.clear();
    }

    private void addMessage(ConversationMessage message) {
        synchronized (conversation) {
            conversation.add(message);
            while (conversation.size() > MAX_HISTORY) {
                conversation.remove(0);
            }
        }
    }
}
