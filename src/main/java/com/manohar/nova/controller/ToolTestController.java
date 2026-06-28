package com.manohar.nova.controller;

import com.manohar.nova.tools.ToolDispatcher;
import com.manohar.nova.tools.ToolRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * Endpoint to run the time tool.
     *
     * @return execution result mapping
     */
    @GetMapping("/time")
    public ResponseEntity<Map<String, String>> executeTimeTool() {
        String result = toolDispatcher.execute("time", "");
        return ResponseEntity.ok(Map.of("result", result));
    }

    /**
     * Data record summarizing tool description parameters.
     */
    public record ToolInfo(String name, String description) {}
}
