package com.ragchat.storage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RenameSessionRequest {
    @NotBlank(message = "New title is required")
    private String newTitle;
}
