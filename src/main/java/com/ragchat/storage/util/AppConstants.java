package com.ragchat.storage.util;

public final class AppConstants {
    private AppConstants() {
    }

    public static final String API_KEY_HEADER = "X-API-KEY";

    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final String ERR_USER_ID_REQUIRED = "User ID is required";
    public static final String ERR_TITLE_REQUIRED = "Title is required";
    public static final String ERR_SENDER_REQUIRED = "Sender is required";
    public static final String ERR_CONTENT_REQUIRED = "Content is required";
    public static final String ERR_SESSION_NOT_FOUND = "Chat session not found with id: ";
    public static final String ERR_TOO_MANY_REQUESTS = "Too many requests";
    public static final String ERR_UNEXPECTED = "An unexpected error occurred";

    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ERRORS = "errors";
    public static final String KEY_DETAILS = "details";

    public static final String REDIS_RATE_LIMIT_PREFIX = "rate_limit:";
}
