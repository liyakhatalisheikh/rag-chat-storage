package com.ragchat.storage.controller;

import com.ragchat.storage.dto.AddMessageRequest;
import com.ragchat.storage.dto.CreateSessionRequest;
import com.ragchat.storage.dto.RenameSessionRequest;
import com.ragchat.storage.model.ChatMessage;
import com.ragchat.storage.model.ChatSession;
import com.ragchat.storage.service.ChatService;
import com.ragchat.storage.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
@Tag(name = "Chat Sessions", description = "API for managing chat sessions and messages")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    @Operation(summary = "Create a new chat session")
    public ResponseEntity<ChatSession> createSession(@Valid @RequestBody CreateSessionRequest request) {
        return new ResponseEntity<>(chatService.createSession(request.getUserId(), request.getTitle()),
                HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all sessions for a user")
    public ResponseEntity<Page<ChatSession>> getUserSessions(@PathVariable String userId,
            @PageableDefault(size = AppConstants.DEFAULT_PAGE_SIZE) Pageable pageable) {
        return ResponseEntity.ok(chatService.getUserSessions(userId, pageable));
    }

    @GetMapping("/{sessionId}")
    @Operation(summary = "Get a specific session")
    public ResponseEntity<ChatSession> getSession(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(chatService.getSession(sessionId));
    }

    @PatchMapping("/{sessionId}/rename")
    @Operation(summary = "Rename a chat session")
    public ResponseEntity<ChatSession> renameSession(@PathVariable UUID sessionId,
            @Valid @RequestBody RenameSessionRequest request) {
        return ResponseEntity.ok(chatService.renameSession(sessionId, request.getNewTitle()));
    }

    @PatchMapping("/{sessionId}/favorite")
    @Operation(summary = "Toggle favorite status of a session")
    public ResponseEntity<ChatSession> toggleFavorite(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(chatService.toggleFavorite(sessionId));
    }

    @DeleteMapping("/{sessionId}")
    @Operation(summary = "Delete a chat session")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable UUID sessionId) {
        chatService.deleteSession(sessionId);
    }

    @PostMapping("/{sessionId}/messages")
    @Operation(summary = "Add a message to a session")
    public ResponseEntity<ChatMessage> addMessage(@PathVariable UUID sessionId,
            @Valid @RequestBody AddMessageRequest request) {
        return new ResponseEntity<>(
                chatService.addMessage(sessionId, request.getSender(), request.getContent(), request.getContext()),
                HttpStatus.CREATED);
    }

    @GetMapping("/{sessionId}/messages")
    @Operation(summary = "Get messages for a session with pagination")
    public ResponseEntity<Page<ChatMessage>> getSessionMessages(@PathVariable UUID sessionId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(chatService.getSessionMessages(sessionId, pageable));
    }
}
