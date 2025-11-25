package com.ragchat.storage.service;

import com.ragchat.storage.exception.ResourceNotFoundException;
import com.ragchat.storage.model.ChatMessage;
import com.ragchat.storage.model.ChatSession;
import com.ragchat.storage.repository.ChatMessageRepository;
import com.ragchat.storage.repository.ChatSessionRepository;
import com.ragchat.storage.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for managing Chat Sessions and Messages.
 * Handles core business logic including session creation, retrieval, updates,
 * and message persistence.
 */
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatSessionRepository sessionRepository;
    private final ChatMessageRepository messageRepository;

    @Transactional
    public ChatSession createSession(String userId, String title) {
        ChatSession session = new ChatSession(userId, title);
        return sessionRepository.save(session);
    }

    public Page<ChatSession> getUserSessions(String userId, Pageable pageable) {
        return sessionRepository.findByUserIdOrderByUpdatedAtDesc(userId, pageable);
    }

    public ChatSession getSession(UUID sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.ERR_SESSION_NOT_FOUND + sessionId));
    }

    @Transactional
    public ChatSession renameSession(UUID sessionId, String newTitle) {
        ChatSession session = getSession(sessionId);
        session.setTitle(newTitle);
        return sessionRepository.save(session);
    }

    @Transactional
    public ChatSession toggleFavorite(UUID sessionId) {
        ChatSession session = getSession(sessionId);
        session.setFavorite(!session.isFavorite());
        return sessionRepository.save(session);
    }

    @Transactional
    public void deleteSession(UUID sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new ResourceNotFoundException("Chat session not found with id: " + sessionId);
        }
        sessionRepository.deleteById(sessionId);
    }

    @Transactional
    public ChatMessage addMessage(UUID sessionId, ChatMessage.Sender sender, String content, String context) {
        ChatSession session = getSession(sessionId);
        ChatMessage message = new ChatMessage(session, sender, content, context);
        return messageRepository.save(message);
    }

    public Page<ChatMessage> getSessionMessages(UUID sessionId, Pageable pageable) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new ResourceNotFoundException("Chat session not found with id: " + sessionId);
        }
        return messageRepository.findBySessionIdOrderByTimestampAsc(sessionId, pageable);
    }
}
