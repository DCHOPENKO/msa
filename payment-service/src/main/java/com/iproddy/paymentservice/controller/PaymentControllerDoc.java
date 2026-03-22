package com.iproddy.paymentservice.controller;

import com.iproddy.common.exception.ErrorResponse;
import com.iproddy.paymentservice.controller.dto.PaymentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(
        name = "API for managing payments",
        description = "Provides CRUD operations for payment entities."
)
public interface PaymentControllerDoc {

    @Operation(
            summary = "Get all payments",
            description = "Returns a list of all payments available in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payments successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDto.Response.Base.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    List<PaymentDto.Response.Base> findAll();

    @Operation(
            summary = "Get payment by id",
            description = "Returns a payment by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment successfully found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDto.Response.Base.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Payment with specified id was not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    PaymentDto.Response.Base findById(
            @Parameter(description = "Unique payment identifier", example = "1", required = true) Long id);

    @Operation(
            summary = "Create payment",
            description = "Creates a new payment using provided request body."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Payment successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDto.Response.Base.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    PaymentDto.Response.Base create(
            @RequestBody(
                    description = "Payment creation request payload", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDto.Request.Base.class)
                    )
            ) PaymentDto.Request.Base request);

    @Operation(
            summary = "Update payment",
            description = "Updates an existing payment by its identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDto.Response.Base.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Payment with specified id was not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    PaymentDto.Response.Base update(
            @Parameter(description = "Unique payment identifier", example = "1", required = true) Long id,
            @RequestBody(
                    description = "Payment update request payload", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDto.Request.Base.class)
                    )
            ) PaymentDto.Request.Base request);

    @Operation(
            summary = "Attempt ti Delete payment",
            description = "Deletes a payment by its identifier if present."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Payment successfully deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    void delete(
            @Parameter(description = "Unique payment identifier", example = "1", required = true) Long id);
}
