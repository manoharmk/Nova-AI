package com.manohar.nova.tools;

import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry component that auto-discovers and indexes all available {@link Tool} beans.
 */
@Component
public class ToolRegistry {

    private final Map<String, Tool> tools = new ConcurrentHashMap<>();

    /**
     * Discovers all Tool beans via constructor injection and indexes them.
     *
     * @param toolList the discovered tools registered in the context
     */
    public ToolRegistry(List<Tool> toolList) {
        if (toolList != null) {
            for (Tool tool : toolList) {
                tools.put(tool.getName(), tool);
            }
        }
    }

    /**
     * Looks up a tool by its unique name.
     *
     * @param toolName the name of the tool to search for
     * @return an Optional containing the tool if found, otherwise empty
     */
    public Optional<Tool> find(String toolName) {
        return Optional.ofNullable(tools.get(toolName));
    }

    /**
     * Gets all registered tools.
     *
     * @return a collection of all tools
     */
    public Collection<Tool> getAll() {
        return tools.values();
    }
}
