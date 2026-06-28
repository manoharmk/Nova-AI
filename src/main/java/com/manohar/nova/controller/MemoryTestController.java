package com.manohar.nova.controller;

import com.manohar.nova.memory.MemoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Temporary REST controller to inspect and manage in-memory conversation history.
 */
@RestController
@RequestMapping("/api/memory")
public class MemoryTestController {

    private final MemoryService memoryService;

    /**
     * Constructor injection for dependency injection.
     *
     * @param memoryService the memory service instance
     */
    public MemoryTestController(MemoryService memoryService) {
        this.memoryService = memoryService;
    }

    /**
     * Endpoint to get the structured conversation history.
     *
     * @return list of serialized conversation messages containing only role and content
     */
    @GetMapping
    public ResponseEntity<List<MessageInfo>> getMemory() {
        List<MessageInfo> messages = memoryService.getConversation().stream()
                .map(msg -> new MessageInfo(msg.getRole(), msg.getContent()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(messages);
    }

    /**
     * Endpoint to clear conversation history.
     *
     * @return response indicating status
     */
    @PostMapping("/clear")
    public ResponseEntity<Void> clearMemory() {
        memoryService.clear();
        return ResponseEntity.ok().build();
    }

    /**
     * Data record capturing only role and content parameters.
     */
    public record MessageInfo(String role, String content) {}
}
