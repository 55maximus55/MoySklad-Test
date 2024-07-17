package ru.maximus.traineeinterviewtaskproject.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.maximus.traineeinterviewtaskproject.entity.Product;

public class ProductSpecification {

    public static Specification<Product> filterBy(ProductFilter productFilter) {
        return Specification.
                where(filterName(productFilter.getName())
                    .and(filterPriceMin(productFilter.getPriceMinimum())
                    .and(filterPriceMax(productFilter.getPriceMaximum()))));
    }

    private static Specification<Product> filterName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null || name.isEmpty() ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    private static Specification<Product> filterPriceMin(Double priceMin) {
        return (root, query, criteriaBuilder) ->
                priceMin == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceMin);
    }

    private static Specification<Product> filterPriceMax(Double priceMax) {
        return (root, query, criteriaBuilder) ->
                priceMax == null ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceMax);
    }

}
