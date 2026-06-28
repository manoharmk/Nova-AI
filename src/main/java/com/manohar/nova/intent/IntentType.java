package com.manohar.nova.intent;

/**
 * Enumeration representing the different types of system intents.
 */
public enum IntentType {
    /**
     * General chat requests handled by the LLM.
     */
    CHAT,

    /**
     * Tool execution requests handled by local system tools.
     */
    TOOL
}
