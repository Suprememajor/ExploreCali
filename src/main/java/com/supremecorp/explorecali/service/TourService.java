package com.supremecorp.explorecali.service;

import com.supremecorp.explorecali.domain.Difficulty;
import com.supremecorp.explorecali.domain.Region;
import com.supremecorp.explorecali.domain.Tour;
import com.supremecorp.explorecali.domain.TourPackage;
import com.supremecorp.explorecali.repository.TourPackageRepository;
import com.supremecorp.explorecali.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    /**
     * Create new tour
     * @param id
     * @param title
     * @param description
     * @param blurb
     * @param price
     * @param duration
     * @param bullets
     * @param keywords
     * @param tourPackageCode
     * @param difficulty
     * @param region
     * @return newly created tour
     */
    public Tour createTour(Integer id, String title, String description, String blurb, Integer price,
                           String duration, String bullets, String keywords, String tourPackageCode,
                           Difficulty difficulty, Region region) {
        TourPackage tourPackage = tourPackageRepository.findById(tourPackageCode)
                .orElseThrow(() -> new RuntimeException("Tour package does not exist."));
        return new Tour(id, title, description, blurb, price, duration,
                bullets, keywords, tourPackage, difficulty, region);
    }

    /**
     *
     * @return number of saved tours
     */
    public long total() {
        return tourRepository.count();
    }
}
