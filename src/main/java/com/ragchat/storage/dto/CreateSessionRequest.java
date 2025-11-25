package com.ragchat.storage.dto;

import com.ragchat.storage.util.AppConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSessionRequest {
    @NotBlank(message = AppConstants.ERR_USER_ID_REQUIRED)
    private String userId;

    @NotBlank(message = AppConstants.ERR_TITLE_REQUIRED)
    private String title;
}
