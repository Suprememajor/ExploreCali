package com.supremecorp.explorecali.web;

import com.supremecorp.explorecali.domain.Tour;
import com.supremecorp.explorecali.domain.TourRating;
import com.supremecorp.explorecali.domain.TourRatingPk;
import com.supremecorp.explorecali.repository.TourRatingRepository;
import com.supremecorp.explorecali.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    private final TourRatingRepository tourRatingRepository;
    private final TourRepository tourRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        Tour tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating(new TourRatingPk(tour, ratingDto.getCustomerId()),
                ratingDto.getScore(), ratingDto.getComment()));
    }

    private Tour verifyTour(Integer tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new NoSuchElementException("Tour with id: '" + tourId + "' does not exist"));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return404(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
