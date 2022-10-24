package com.supremecorp.explorecali.web;

import com.supremecorp.explorecali.domain.TourRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RatingDto {
    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Integer customerId;

    public RatingDto(TourRating tourRating) {
        this.score = tourRating.getScore();
        this.comment = tourRating.getComment();
        this.customerId = tourRating.getPk().getCustomerId();
    }
}
