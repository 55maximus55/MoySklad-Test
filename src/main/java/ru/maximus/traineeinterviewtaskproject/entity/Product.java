package ru.maximus.traineeinterviewtaskproject.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean inStock;

}
