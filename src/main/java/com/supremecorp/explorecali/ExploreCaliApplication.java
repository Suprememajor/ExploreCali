package com.supremecorp.explorecali;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supremecorp.explorecali.domain.Difficulty;
import com.supremecorp.explorecali.domain.Region;
import com.supremecorp.explorecali.service.TourPackageService;
import com.supremecorp.explorecali.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class ExploreCaliApplication implements CommandLineRunner {
    private final TourService tourService;
    private final TourPackageService tourPackageService;

    public static void main(String[] args) {
        SpringApplication.run(ExploreCaliApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createTourPackages();
        long numOfPackages = tourPackageService.total();
        createTours("ExploreCameroon.json");
//        long numOfTours = tourService.total();
    }

    private void createTours(String fileToImport) throws IOException {
        TourFromFile.read(fileToImport).forEach(importedTour ->
                tourService.createTour(
                        importedTour.getTitle(),
                        importedTour.getDescription(),
                        importedTour.getBlurb(),
                        importedTour.getPrice(),
                        importedTour.getLength(),
                        importedTour.getBullets(),
                        importedTour.getKeywords(),
                        importedTour.getPackageType(),
                        importedTour.getDifficulty(),
                        importedTour.getRegion()
                )
        );
    }

    private void createTourPackages() {
        tourPackageService.createTourPackage("MO", "The Monument");
        tourPackageService.createTourPackage("KB", "Kumba");
        tourPackageService.createTourPackage("DL", "Douala");
        tourPackageService.createTourPackage("LB", "Limbe");
        tourPackageService.createTourPackage("BD", "Bamenda");
        tourPackageService.createTourPackage("TK", "Tiko");
        tourPackageService.createTourPackage("MC", "Mount Cameroon");
        tourPackageService.createTourPackage("BT", "Botanical Garden");
        tourPackageService.createTourPackage("SE", "Seme Tour");
    }

    private static class TourFromFile {
        private String packageType, title, description, blurb, price, length, bullets,
                keywords, difficulty, region;

        static List<TourFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                    .readValue(new FileInputStream(fileToImport), new TypeReference<>() {
                    });
        }

        protected TourFromFile() {
        }

        public String getPackageType() {
            return packageType;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getBlurb() {
            return blurb;
        }

        public Integer getPrice() {
            return Integer.parseInt(price);
        }

        public String getLength() {
            return length;
        }

        public String getBullets() {
            return bullets;
        }

        public String getKeywords() {
            return keywords;
        }

        public Difficulty getDifficulty() {
            return Difficulty.valueOf(difficulty);
        }

        public Region getRegion() {
            return Region.findByLabel(region);
        }
    }
}
