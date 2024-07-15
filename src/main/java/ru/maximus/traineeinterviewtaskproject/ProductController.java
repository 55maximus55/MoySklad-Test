package ru.maximus.traineeinterviewtaskproject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ProductController {

    private HashMap<Long, Product> products = new HashMap<>();
    private long nextProductId = 0;

    private void insertProduct(Product product) {
        product.setId(nextProductId++);
        products.put(product.getId(), product);
    }

    public ProductController() {
        insertProduct(new Product("Lorem", "product 1", 2.0, true));
        insertProduct(new Product("Ipsum", "product 2", 3.0, false));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable long id) {
        if (products.containsKey(id)) {
            return new ResponseEntity<>(products.get(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/products/all")
    public ResponseEntity<Object> getAllProducts() {
        return new ResponseEntity<>(products.values(), HttpStatus.OK);
    }

}
