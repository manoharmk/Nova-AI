package com.manohar.nova.memory;

import java.time.Instant;

/**
 * Value object representing a single message in the conversation history.
 */
public class ConversationMessage {

    private final String role;
    private final String content;
    private final Instant timestamp;

    /**
     * Constructs a new ConversationMessage with the current timestamp.
     *
     * @param role    the role of the message sender (e.g., "user", "assistant")
     * @param content the text content of the message
     */
    public ConversationMessage(String role, String content) {
        this.role = role;
        this.content = content;
        this.timestamp = Instant.now();
    }

    /**
     * Constructs a new ConversationMessage with an explicit timestamp.
     *
     * @param role      the role of the message sender
     * @param content   the text content of the message
     * @param timestamp the creation timestamp of the message
     */
    public ConversationMessage(String role, String content, Instant timestamp) {
        this.role = role;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
