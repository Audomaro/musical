package com.expeditors.musicalpricing.services;

import com.expeditors.musicalpricing.repositories.PricingRepository;
import org.springframework.stereotype.Service;

@Service
public class PricingServiceImpl implements PricingService {
    private final PricingRepository pricingRepository;


    public PricingServiceImpl(PricingRepository pricingRepository) {
        this.pricingRepository = pricingRepository;
    }

    @Override
    public double getPrice(int idTrack) {
        return pricingRepository.getPrice(idTrack);
    }
}
