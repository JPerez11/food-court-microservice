package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.TraceabilityDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.TraceabilityHandler;
import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
@RequestMapping("/traceability")
public class TraceabilityRestController {

    private final TraceabilityHandler traceabilityHandler;

    @Secured({Constants.CUSTOMER_ROLE_NAME})
    @Operation(summary = "Get history",
            responses = {
                    @ApiResponse(responseCode = "201", description = "history returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Conflict",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/")
    public ResponseEntity<List<TraceabilityDto>> getHistory() {
        return ResponseEntity.ok(traceabilityHandler.getHistory());
    }

}
