package com.iproddy.common.util.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AsyncConstants {

    public static final String IDEMPOTENT_KEY_HEADER_NAME = "X-Idempotency-Key";

}
