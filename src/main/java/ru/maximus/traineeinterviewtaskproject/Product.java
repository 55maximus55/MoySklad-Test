package ru.maximus.traineeinterviewtaskproject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price = 0.0;
    private boolean inStock = false;

    public Product(String name, String description, Double price, boolean inStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.inStock = inStock;
    }
}
