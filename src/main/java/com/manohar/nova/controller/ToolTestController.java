package com.manohar.nova.controller;

import com.manohar.nova.tools.ToolDispatcher;
import com.manohar.nova.tools.ToolRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Temporary REST controller for testing registered tools.
 */
@RestController
@RequestMapping("/api/tools")
public class ToolTestController {

    private final ToolRegistry toolRegistry;
    private final ToolDispatcher toolDispatcher;

    /**
     * Constructor injection for dependency injection.
     *
     * @param toolRegistry   the tool registry instance
     * @param toolDispatcher the tool dispatcher instance
     */
    public ToolTestController(ToolRegistry toolRegistry, ToolDispatcher toolDispatcher) {
        this.toolRegistry = toolRegistry;
        this.toolDispatcher = toolDispatcher;
    }

    /**
     * Endpoint to list all registered tools.
     *
     * @return list of tool information objects
     */
    @GetMapping
    public ResponseEntity<List<ToolInfo>> getTools() {
        List<ToolInfo> toolsList = toolRegistry.getAll().stream()
                .map(tool -> new ToolInfo(tool.getName(), tool.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(toolsList);
    }

    /**
     * Endpoint to run any registered tool with optional inputs.
     *
     * @param tool  the name of the tool to execute
     * @param input the optional execution argument
     * @return execution result mapping
     */
    @GetMapping("/{tool}")
    public ResponseEntity<Map<String, String>> executeTool(
            @PathVariable String tool,
            @RequestParam(required = false, defaultValue = "") String input) {
        String result = toolDispatcher.execute(tool, input);
        return ResponseEntity.ok(Map.of("result", result));
    }

    /**
     * Data record summarizing tool description parameters.
     */
    public record ToolInfo(String name, String description) {}
}
