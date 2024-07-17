package ru.maximus.traineeinterviewtaskproject.service;

import org.apache.coyote.BadRequestException;
import ru.maximus.traineeinterviewtaskproject.entity.Product;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;

public class ProductService {

    private final HashMap<Long, Product> products = new HashMap<>();
    private long nextProductId = 0;

    public Product getProduct(long id) {
        return products.get(id);
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public void addProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getDescription() == null) {
            product.setDescription("");
        } else if (product.getDescription().length() > 4096) {
            throw new IllegalArgumentException("Product description cannot be longer than 4096");
        }

        if (product.getPrice() == null) {
            product.setPrice(0.0);
        } else if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }

        if (product.getInStock() == null) {
            product.setInStock(false);
        }

        product.setId(nextProductId++);
        products.put(product.getId(), product);
    }

    public Product updateProduct(Product updateProduct) throws Exception {
        if (updateProduct.getId() == null) throw new BadRequestException("Product id cannot be null");
        Product product = getProduct(updateProduct.getId());
        if (product == null) throw new FileNotFoundException("Product with id " + updateProduct.getId() + " not found");

        if (updateProduct.getName() != null && !updateProduct.getName().isEmpty()) {
            product.setName(updateProduct.getName());
        }
        if (updateProduct.getDescription() != null && updateProduct.getDescription().length() <= 4096) {
            product.setDescription(updateProduct.getDescription());
        }
        if (updateProduct.getPrice() != null && !(updateProduct.getPrice() < 0)) {
            product.setPrice(updateProduct.getPrice());
        }
        if (updateProduct.getInStock() != null) product.setInStock(updateProduct.getInStock());

        return product;
    }

    public void deleteProduct(long id) throws FileNotFoundException {
        if (products.containsKey(id))
            products.remove(id);
        else
            throw new FileNotFoundException("Product with id " + id + " not found");
    }

}
