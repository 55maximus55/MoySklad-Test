package ru.maximus.traineeinterviewtaskproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maximus.traineeinterviewtaskproject.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
