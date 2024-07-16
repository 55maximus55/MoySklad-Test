package ru.maximus.traineeinterviewtaskproject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductRepository productRepository = new ProductRepository();

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable long id) {
        Product product = productRepository.getProduct(id);
        if (product == null) {
            return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(), "Product with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/products/all")
    public ResponseEntity<Object> getAllProducts() {
        return new ResponseEntity<>(productRepository.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/products/create")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        String error = productRepository.addProduct(product);
        if (error != null) {
            return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), error), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

}
