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

    @PutMapping("/products/update")
    public ResponseEntity<Object> updateProduct(@RequestBody Product updateProduct) {
        if (updateProduct.getId() == null) return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Product id is null"), HttpStatus.BAD_REQUEST);
        Product product = productRepository.getProduct(updateProduct.getId());
        if (product == null) {
            return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Product with id " + updateProduct.getId() + " not found"), HttpStatus.BAD_REQUEST);
        }

        if (updateProduct.getName() != null && updateProduct.getName().isEmpty()) {
            return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Product name can't be empty"), HttpStatus.BAD_REQUEST);
        }
        if (updateProduct.getDescription() != null && updateProduct.getDescription().length() > 4096) {
            return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Product description is longer than 4096 characters"), HttpStatus.BAD_REQUEST);
        }
        if (updateProduct.getPrice() != null && updateProduct.getPrice() < 0) {
            return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Product price is negative"), HttpStatus.BAD_REQUEST);
        }

        if (updateProduct.getName() != null) product.setName(updateProduct.getName());
        if (updateProduct.getDescription() != null) product.setDescription(updateProduct.getDescription());
        if (updateProduct.getPrice() != null) product.setPrice(updateProduct.getPrice());
        if (updateProduct.getInStock() != null) product.setInStock(updateProduct.getInStock());

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/products/delete")
    public ResponseEntity<Object> deleteProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Product id is null"), HttpStatus.BAD_REQUEST);
        }
        if (productRepository.getProduct(product.getId()) == null) {
            return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(), "Product with id " + product.getId() + " not found"), HttpStatus.NOT_FOUND);
        }

        productRepository.deleteProduct(product.getId());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
