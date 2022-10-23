package com.supremecorp.explorecali.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column(length = 2000)
    private String description;
    @Column(length = 2000)
    private String blurb;
    @Column
    private Integer price;
    @Column
    private String duration;
    @Column(length = 2000)
    private String bullets;
    @Column
    private String keywords;
    @ManyToOne
    private TourPackage tourPackage;
    @Column
    @Enumerated
    private Difficulty difficulty;
    @Column
    @Enumerated
    private Region region;

    protected Tour() {}
}
