package com.manohar.nova.tools;

/**
 * Interface that all system tools must implement.
 */
public interface Tool {

    /**
     * Gets the unique name of the tool.
     *
     * @return the tool's name
     */
    String getName();

    /**
     * Gets a description of what the tool does.
     *
     * @return the tool's description
     */
    String getDescription();

    /**
     * Executes the tool with the given input string.
     *
     * @param input the input parameter for execution
     * @return the result of tool execution
     */
    String execute(String input);
}
