package com.iproddy.deliveryservice.controller;

import com.iproddy.common.exception.ErrorResponse;
import com.iproddy.deliveryservice.controller.dto.DeliveryDto;
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
        name = "API for managing deliveries",
        description = "API for managing deliveries. Provides CRUD operations for delivery entities."
)
public interface DeliveryControllerDoc {

    @Operation(
            summary = "Get all deliveries",
            description = "Returns a list of all deliveries available in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deliveries successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryDto.Response.Base.class)
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
    List<DeliveryDto.Response.Base> findAll();

    @Operation(
            summary = "Get delivery by id",
            description = "Returns a delivery by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Delivery successfully found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryDto.Response.Base.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Delivery with specified id was not found",
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
    DeliveryDto.Response.Base findById(
            @Parameter(description = "Unique delivery identifier", example = "1", required = true)  Long id);

    @Operation(
            summary = "Create delivery",
            description = "Creates a new delivery using provided request body."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Delivery successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryDto.Response.Base.class)
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
    DeliveryDto.Response.Base create(
            @RequestBody(
                    description = "Delivery creation request payload", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryDto.Request.Base.class)
                            )
                    ) DeliveryDto.Request.Base request);


    @Operation(
            summary = "Update delivery",
            description = "Updates an existing delivery by its identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Delivery successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryDto.Response.Base.class)
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
                    description = "Delivery with specified id was not found",
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
    DeliveryDto.Response.Base update(
            @Parameter(description = "Unique delivery identifier", example = "1", required = true)   Long id,
                                     @RequestBody(
                                             description = "Delivery update request payload", required = true,
                                             content = @Content(
                                                     mediaType = "application/json",
                                                     schema = @Schema(implementation = DeliveryDto.Request.Base.class)
                                             )
                                     ) DeliveryDto.Request.Base request);

    @Operation(
            summary = "Attempt ti Delete delivery",
            description = "Deletes a delivery by its identifier if present."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Delivery successfully deleted",
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
    void delete(@Parameter(description = "Unique delivery identifier", example = "1", required = true)   Long id);
}
