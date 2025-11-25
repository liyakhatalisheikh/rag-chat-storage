package com.ragchat.storage.interceptor;

import com.ragchat.storage.service.RateLimiterService;
import com.ragchat.storage.util.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String apiKey = request.getHeader(AppConstants.API_KEY_HEADER);
        if (apiKey == null || apiKey.isEmpty()) {
            return true;
        }

        if (rateLimiter.isAllowed(apiKey)) {
            return true;
        } else {
            response.setStatus(429);
            response.getWriter().write(AppConstants.ERR_TOO_MANY_REQUESTS);
            return false;
        }
    }
}
