package ru.maximus.traineeinterviewtaskproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximus.traineeinterviewtaskproject.service.ProductService;
import ru.maximus.traineeinterviewtaskproject.entity.Product;

import java.io.FileNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private final ProductService productService = new ProductService();

    @GetMapping("")
    public ResponseEntity<Object> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getProduct(@PathVariable long id) throws Exception {
        Product product = productService.getProduct(id);
        if (product == null) throw new FileNotFoundException("Product with id " + id + " not found");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) throws Exception {
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<Object> updateProduct(@RequestBody Product updateProduct) throws Exception {
        Product product = productService.updateProduct(updateProduct);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteProduct(@RequestBody Product product) {
        productService.deleteProduct(product.getId());
        return new ResponseEntity<>(Map.of(
                "status", HttpStatus.OK.value(),
                "message", "Product with id " + product.getId() + " was deleted"
        ), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception ex) {
        return Map.of(
                "message", ex.getMessage(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, Object> handleException(IllegalArgumentException ex) {
        return Map.of(
                "message", ex.getMessage(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException.class)
    public Map<String, Object> handleException(FileNotFoundException ex) {
        return Map.of(
                "message", ex.getMessage(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase()
        );
    }

}
