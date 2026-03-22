package com.iproddy.orderservice.controller;

import com.iproddy.common.exception.ErrorResponse;
import com.iproddy.orderservice.controller.dto.OrderDto;
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
        name = "API for managing customer orders",
        description = "Provides CRUD operations for order entities"
)
public interface OrderControllerDoc {

    @Operation(
            summary = "Get all orders",
            description = "Returns a list of all orders available in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.Response.Base.class)
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
    List<OrderDto.Response.Base> findAll();

    @Operation(
            summary = "Get order by id",
            description = "Returns a order by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order successfully found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.Response.Base.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order with specified id was not found",
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
    OrderDto.Response.Base findById(
            @Parameter(description = "Unique order identifier", example = "1", required = true) Long id);

    @Operation(
            summary = "Create order",
            description = "Creates a new order. If paymentMethod is provided, the service also sends a request to payment-service " +
                    "to create a payment for the created order and stores the returned payment identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Order successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.Response.Base.class)
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
    OrderDto.Response.Base create(
            @RequestBody(
                    description = "Order creation request payload", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.Request.Create.class)
                    )
            ) OrderDto.Request.Create request);

    @Operation(
            summary = "Update order",
            description = "Updates an existing order by its identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.Response.Base.class)
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
                    description = "Order with specified id was not found",
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
    OrderDto.Response.Base update(
            @Parameter(description = "Unique order identifier", example = "1", required = true) Long id,
            @RequestBody(
                    description = "Order update request payload", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.Request.Update.class)
                    )
            )
            OrderDto.Request.Update request);

    @Operation(
            summary = "Attempt ti Delete order",
            description = "Deletes a order by its identifier if present."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Order successfully deleted",
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
            @Parameter(description = "Unique order identifier", example = "1", required = true) Long id);
}
