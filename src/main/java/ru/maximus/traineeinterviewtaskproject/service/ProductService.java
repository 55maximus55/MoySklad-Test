package ru.maximus.traineeinterviewtaskproject.service;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maximus.traineeinterviewtaskproject.entity.Product;
import ru.maximus.traineeinterviewtaskproject.filter.ProductFilter;
import ru.maximus.traineeinterviewtaskproject.filter.ProductSpecification;
import ru.maximus.traineeinterviewtaskproject.repository.ProductRepository;

import java.io.FileNotFoundException;
import java.util.Collection;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(long id) throws FileNotFoundException {
        if (productRepository.existsById(id))
            return productRepository.findById(id).get();
        else throw new FileNotFoundException("Product with " + id + " not found");
    }

    public Collection<Product> getAllProducts(
            String sortBy, String order, String priceMin, String priceMax, String name
    ) {

        Double minPrice = priceMin == null ? null : Double.valueOf(priceMin);
        Double maxPrice = priceMax == null ? null : Double.valueOf(priceMax);

        return productRepository.findAll(
                ProductSpecification.filterBy(new ProductFilter(name, minPrice, maxPrice))
        );
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

        productRepository.save(product);
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

        productRepository.save(product);
        return product;
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

}
