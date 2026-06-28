package com.manohar.nova.controller;

import com.manohar.nova.dto.ChatRequest;
import com.manohar.nova.dto.ChatResponse;
import com.manohar.nova.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller providing chat API endpoints.
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    /**
     * Constructor injection for dependency injection.
     *
     * @param chatService the chat service bean
     */
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Handles chat POST requests, validates input, and returns a response.
     *
     * @param request the validated chat request payload
     * @return the HTTP response entity containing the chat response
     */
    @PostMapping
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        ChatResponse response = chatService.chat(request);
        return ResponseEntity.ok(response);
    }
}
