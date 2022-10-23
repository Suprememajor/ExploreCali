package com.supremecorp.explorecali.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class TourPackage {
    @Id
    private String code;
    @Column
    private  String name;
    protected TourPackage() {}
}
