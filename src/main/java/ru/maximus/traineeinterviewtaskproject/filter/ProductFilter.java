package ru.maximus.traineeinterviewtaskproject.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductFilter {

    private String name;
    private Double priceMinimum;
    private Double priceMaximum;

}
