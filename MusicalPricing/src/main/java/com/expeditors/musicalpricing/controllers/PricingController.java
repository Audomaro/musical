package com.expeditors.musicalpricing.controllers;

import com.expeditors.musicalpricing.services.PricingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pricing")
public class PricingController {

private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getPrice(@PathVariable int id) {
        return ResponseEntity.ok(pricingService.getPrice(id));
    }
}
