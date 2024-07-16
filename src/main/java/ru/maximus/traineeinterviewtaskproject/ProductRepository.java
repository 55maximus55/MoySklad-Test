package ru.maximus.traineeinterviewtaskproject;

import java.util.Collection;
import java.util.HashMap;

public class ProductRepository {

    private final HashMap<Long, Product> products = new HashMap<>();
    private long nextProductId = 0;

    public Product getProduct(long id) {
        return products.get(id);
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public String addProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            return "Product name can't be empty";
        }

        if (product.getDescription() == null) {
            product.setDescription("");
        } else if (product.getDescription().length() > 4096) {
            return "Product description is longer than 4096 characters";
        }

        if (product.getPrice() == null) {
            product.setPrice(0.0);
        } else if (product.getPrice() < 0) {
            return "Product price can't be negative";
        }

        if (product.getInStock() == null) {
            product.setInStock(false);
        }

        product.setId(nextProductId++);
        products.put(product.getId(), product);
        return null;
    }

}
