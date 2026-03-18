package com.iproddy.paymentservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public enum CardInfoDto {
    ;

    public enum Request {
        ;
        @Schema(name = "CardInfoBaseRequest", description = "Card information used for payment request")
        public record Base(
                @Schema(description = "Card holder full name as printed on the card", example = "IVAN IVANOV", requiredMode = Schema.RequiredMode.REQUIRED)
                String cardHolder,
                @Schema(description = "Bank card number. Digits only, without spaces.", example = "4111111111111111", requiredMode = Schema.RequiredMode.REQUIRED)
                String cardNumber,
                @Schema(description = "Card expiration month", example = "12", minimum = "1", maximum = "12", requiredMode = Schema.RequiredMode.REQUIRED)
                Integer expirationMonth,
                @Schema(description = "Card expiration year", example = "2028", minimum = "2026", maximum = "2100", requiredMode = Schema.RequiredMode.REQUIRED)
                Integer expirationYear
        ) {
        }
    }

    public enum Response {
        ;
        @Schema(name = "CardInfoBaseResponse", description = "Card information used for payment response")
        public record Base(
                @Schema(description = "Card holder full name as printed on the card", example = "IVAN IVANOV", requiredMode = Schema.RequiredMode.REQUIRED)
                String cardHolder,
                @Schema(description = "Bank card number. Digits only, without spaces.", example = "4111111111111111", requiredMode = Schema.RequiredMode.REQUIRED)
                String cardNumber,
                @Schema(description = "Card expiration month", example = "12", minimum = "1", maximum = "12", requiredMode = Schema.RequiredMode.REQUIRED)
                Integer expirationMonth,
                @Schema(description = "Card expiration year", example = "2028", minimum = "2026", maximum = "2100", requiredMode = Schema.RequiredMode.REQUIRED)
                Integer expirationYear
        ) {
        }
    }
}