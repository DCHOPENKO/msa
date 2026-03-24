package com.iproddy.paymentservice.web.interceptor;

import com.iproddy.common.exception.IdempotencyKeyExistsException;
import com.iproddy.paymentservice.model.entity.IdempotencyKey;
import com.iproddy.paymentservice.model.enums.IdempotencyKeyStatus;
import com.iproddy.paymentservice.service.IdempotencyKeyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Optional;

import static com.iproddy.common.util.constant.WebConstants.APPLICABLE_HTTP_METHODS_FOR_IDEMPOTENCY;
import static com.iproddy.common.util.constant.WebConstants.IDEMPOTENT_KEY_HEADER_NAME;
import static com.iproddy.common.util.constant.WebConstants.WRAPPED_RESPONSE_ATTRIBUTE_NAME;

@Component
@RequiredArgsConstructor
public class IdempotencyInterceptor implements HandlerInterceptor {

    private final IdempotencyKeyService idempotencyKeyService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        if (APPLICABLE_HTTP_METHODS_FOR_IDEMPOTENCY.contains(method.name())) {
            var idempotencyKeyHeader = request.getHeader(IDEMPOTENT_KEY_HEADER_NAME);
            if (StringUtils.isBlank(idempotencyKeyHeader)) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.getWriter().println("%s is not present".formatted(IDEMPOTENT_KEY_HEADER_NAME));
                return false;
            }
            long idempotencyKeyId;
            try {
                idempotencyKeyId = Long.parseLong(idempotencyKeyHeader);
            } catch (NumberFormatException e) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.getWriter().println("%s must be a valid UUID".formatted(IDEMPOTENT_KEY_HEADER_NAME));
                return false;
            }
            return processIdempotency(idempotencyKeyId, response);
        }
        return true;
    }

    private boolean processIdempotency(Long idempotencyKeyId,
                                       HttpServletResponse response) throws IOException {

        Optional<IdempotencyKey> optIdempotencyKey = idempotencyKeyService.findById(idempotencyKeyId);
        return optIdempotencyKey.isPresent() ? processExistingKey(optIdempotencyKey.get(), response) : createNewKey(idempotencyKeyId, response);
    }

    private boolean processExistingKey(IdempotencyKey idempotencyKey,
                                       HttpServletResponse response) throws IOException {
        IdempotencyKeyStatus status = idempotencyKey.getStatus();

        switch (status) {
            case PENDING -> {
                response.setStatus(HttpStatus.CONFLICT.value());
                response.getWriter().println("Same request is already in progress...");
            }
            case COMPLETED -> {
                response.setStatus(idempotencyKey.getStatusCode());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().println(idempotencyKey.getResponse());
            }
            default -> throw new IllegalArgumentException("Invalid status of idempotency key");
        }
        return false;
    }

    private boolean createNewKey(Long idempotencyKeyId,
                                 HttpServletResponse response) throws IOException {
        try {
            idempotencyKeyService.create(idempotencyKeyId);
            return true;
        } catch (IdempotencyKeyExistsException e) {
            response.setStatus(HttpStatus.CONFLICT.value());
            response.getWriter().println("Same request is already in progress...");
            return false;
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) throws Exception {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        if (APPLICABLE_HTTP_METHODS_FOR_IDEMPOTENCY.contains(method.name())) {
            ContentCachingResponseWrapper wrappedResponse = (ContentCachingResponseWrapper) request.getAttribute(WRAPPED_RESPONSE_ATTRIBUTE_NAME);
            String responseBody = new String(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());

            long idempotencyKeyId = Long.parseLong(request.getHeader(IDEMPOTENT_KEY_HEADER_NAME));
            idempotencyKeyService.markAsCompleted(idempotencyKeyId, responseBody, response.getStatus());
        }
    }

}
