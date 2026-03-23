package com.iproddy.common.util.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebConstants {

    public static final Set<String> APPLICABLE_HTTP_METHODS_FOR_IDEMPOTENCY = Set.of("POST", "PUT", "PATCH");
    public static final String IDEMPOTENT_KEY_HEADER_NAME = "X-Idempotency-Key";
    public static final String WRAPPED_RESPONSE_ATTRIBUTE_NAME = "wrappedResponse";

}
