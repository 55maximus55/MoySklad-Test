package ru.maximus.traineeinterviewtaskproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximus.traineeinterviewtaskproject.Error;
import ru.maximus.traineeinterviewtaskproject.service.ProductService;
import ru.maximus.traineeinterviewtaskproject.entity.Product;

import java.util.Map;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService = new ProductService();

    @GetMapping("{id}")
    public ResponseEntity<Object> getProduct(@PathVariable long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(), "Product with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) throws Exception {
        String error = productService.addProduct(product);
        if (error != null) {
            throw new Exception(error);
//            return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), error), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<Object> updateProduct(@RequestBody Product updateProduct) {
        if (updateProduct.getId() == null) return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Product id is null"), HttpStatus.BAD_REQUEST);
        Product product = productService.getProduct(updateProduct.getId());
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

    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Product id is null"), HttpStatus.BAD_REQUEST);
        }
        if (productService.getProduct(product.getId()) == null) {
            return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(), "Product with id " + product.getId() + " not found"), HttpStatus.NOT_FOUND);
        }

        productService.deleteProduct(product.getId());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
