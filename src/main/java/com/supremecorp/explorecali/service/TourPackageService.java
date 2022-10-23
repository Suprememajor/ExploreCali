package com.supremecorp.explorecali.service;

import com.supremecorp.explorecali.domain.TourPackage;
import com.supremecorp.explorecali.repository.TourPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TourPackageService {
    private final TourPackageRepository tourPackageRepository;

    /**
     * Create a tour package
     * @param code tour package id
     * @param name tour package name
     * @return tour package
     */
    public TourPackage createTourPackage(String code, String name) {
        return tourPackageRepository.findById(code)
                .orElse(tourPackageRepository.save(new TourPackage(code, name)));
    }

    /**
     *
     * @return all tour packages
     */
    public Iterable<TourPackage> lookup() {
        return tourPackageRepository.findAll();
    }

    /**
     *
     * @return number of tour packages
     */
    public long total() {
        return tourPackageRepository.count();
    }
}
