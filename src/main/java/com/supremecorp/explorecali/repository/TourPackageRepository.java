package com.supremecorp.explorecali.repository;

import com.supremecorp.explorecali.domain.TourPackage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TourPackageRepository extends CrudRepository<TourPackage, String> {
    Optional<TourPackage> findByNameIgnoreCase(String name);
}
