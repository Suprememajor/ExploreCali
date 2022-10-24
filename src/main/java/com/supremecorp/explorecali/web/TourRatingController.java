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

import java.util.List;
import java.util.Map;
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

    @GetMapping
    public List<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        return tourRatingRepository.findByPkTourId(tourId)
                .stream().map(RatingDto::new)
                .toList();
    }


    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByPkTourId(tourId)
                .stream().mapToInt(TourRating::getScore).average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings")));
    }

    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        rating.setScore(ratingDto.getScore());
        rating.setComment(ratingDto.getComment());
        return new RatingDto(tourRatingRepository.save(rating));
    }

    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        if (ratingDto.getScore() != null) {
            rating.setScore(ratingDto.getScore());
        }
        if (ratingDto.getComment() != null) {
            rating.setComment(ratingDto.getComment());
        }
        return new RatingDto(tourRatingRepository.save(rating));
    }

    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    private Tour verifyTour(Integer tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new NoSuchElementException("Tour with id: '" + tourId + "' does not exist"));
    }

    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByPkTourIdAndAndPkCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("Tour-Rating pair request(" + tourId +
                        " for customer " + customerId + ")"));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return404(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
