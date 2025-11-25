package com.ragchat.storage.service;

import com.ragchat.storage.model.ChatSession;
import com.ragchat.storage.repository.ChatMessageRepository;
import com.ragchat.storage.repository.ChatSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatSessionRepository sessionRepository;

    @Mock
    private ChatMessageRepository messageRepository;

    @InjectMocks
    private ChatService chatService;

    private ChatSession session;

    @BeforeEach
    void setUp() {
        session = new ChatSession("user1", "Test Session");
        session.setId(UUID.randomUUID());
    }

    @Test
    void createSession_ShouldReturnSavedSession() {
        when(sessionRepository.save(any(ChatSession.class))).thenReturn(session);

        ChatSession created = chatService.createSession("user1", "Test Session");

        assertNotNull(created);
        assertEquals("user1", created.getUserId());
        assertEquals("Test Session", created.getTitle());
        verify(sessionRepository).save(any(ChatSession.class));
    }

    @Test
    void getSession_ShouldReturnSession_WhenExists() {
        when(sessionRepository.findById(session.getId())).thenReturn(Optional.of(session));

        ChatSession found = chatService.getSession(session.getId());

        assertNotNull(found);
        assertEquals(session.getId(), found.getId());
    }

    @Test
    void renameSession_ShouldUpdateTitle() {
        when(sessionRepository.findById(session.getId())).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(ChatSession.class))).thenReturn(session);

        ChatSession updated = chatService.renameSession(session.getId(), "New Title");

        assertEquals("New Title", updated.getTitle());
        verify(sessionRepository).save(session);
    }
}
