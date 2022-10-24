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
     * @param title
     * @param description
     * @param blurb
     * @param price
     * @param duration
     * @param bullets
     * @param keywords
     * @param tourPackageName
     * @param difficulty
     * @param region
     * @return newly created tour
     */
    public Tour createTour(String title, String description, String blurb, Integer price,
                           String duration, String bullets, String keywords, String tourPackageName,
                           Difficulty difficulty, Region region) {
        TourPackage tourPackage = tourPackageRepository.findByNameIgnoreCase(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour package does not exist."));
        return tourRepository.save(new Tour(title, description, blurb, price, duration,
                bullets, keywords, tourPackage, difficulty, region));
    }

    /**
     *
     * @return number of saved tours
     */
    public long total() {
        return tourRepository.count();
    }
}
