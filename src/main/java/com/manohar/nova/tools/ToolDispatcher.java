package com.manohar.nova.tools;

import org.springframework.stereotype.Component;

/**
 * Dispatcher component that executes registered tools or returns an error message.
 */
@Component
public class ToolDispatcher {

    private final ToolRegistry toolRegistry;

    /**
     * Constructor injection for dependency injection.
     *
     * @param toolRegistry the tool registry instance
     */
    public ToolDispatcher(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
    }

    /**
     * Dispatches execution to the specified tool if it exists.
     *
     * @param toolName the name of the tool to execute
     * @param input    the input arguments to pass to the tool
     * @return the execution response or an unknown tool error message
     */
    public String execute(String toolName, String input) {
        return toolRegistry.find(toolName)
                .map(tool -> tool.execute(input))
                .orElse("Unknown tool: " + toolName);
    }
}
