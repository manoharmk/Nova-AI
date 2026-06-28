package com.manohar.nova.intent;

/**
 * Value object representing a resolved request intent.
 */
public class Intent {

    private final IntentType type;
    private final String toolName;
    private final String originalMessage;

    /**
     * Constructs a new Intent.
     *
     * @param type            the type of intent
     * @param toolName        the name of the tool (if applicable)
     * @param originalMessage the original user prompt
     */
    public Intent(IntentType type, String toolName, String originalMessage) {
        this.type = type;
        this.toolName = toolName;
        this.originalMessage = originalMessage;
    }

    public IntentType getType() {
        return type;
    }

    public String getToolName() {
        return toolName;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }
}
