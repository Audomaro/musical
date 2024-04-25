package com.expeditors.musicalpricing.controllers;

import com.expeditors.musicalpricing.services.PricingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Pricing Controller", description = "Contains functions related to the price of tracks")
@RestController
@RequestMapping("/pricing")
public class PricingController {

private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Operation(
            description = "Return a price for a specific track"
    )
    @GetMapping("/{id}")
    ResponseEntity<?> getPrice(
            @Parameter(description = "ID of track", required = true)
            @PathVariable int id) {
        return ResponseEntity.ok(pricingService.getPrice(id));
    }
}
