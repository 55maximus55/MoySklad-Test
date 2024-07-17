package ru.maximus.traineeinterviewtaskproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;
    @Column(length = 4096)
    private String description;
    private Double price;
    private Boolean inStock;

}
