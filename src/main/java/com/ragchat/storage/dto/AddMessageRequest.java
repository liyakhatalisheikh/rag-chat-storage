package com.ragchat.storage.dto;

import com.ragchat.storage.model.ChatMessage;
import com.ragchat.storage.util.AppConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddMessageRequest {
    @NotNull(message = AppConstants.ERR_SENDER_REQUIRED)
    private ChatMessage.Sender sender;

    @NotBlank(message = AppConstants.ERR_CONTENT_REQUIRED)
    private String content;

    private String context;
}
