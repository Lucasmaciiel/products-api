package com.desafiojavaspringboot.uol.api.service;

import com.desafiojavaspringboot.uol.api.model.Product;
import com.desafiojavaspringboot.uol.api.repository.ProductRepository;
import com.desafiojavaspringboot.uol.api.repository.filter.FilterMaxPrice;
import com.desafiojavaspringboot.uol.api.repository.filter.FilterMinPrice;
import com.desafiojavaspringboot.uol.api.repository.filter.FilterNameDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product save(Product product) {
        var productExisting = this.findById(product.getId()).get();

        if (productExisting != null)
            throw new EntityExistsException("Product already exists", new ResponseStatusException(HttpStatus.BAD_REQUEST));

        return repository.save(product);
    }

    public void updateProduct(Integer id, Product product) {
        this.repository.findById(id)
                .map(productExisting -> {
                    product.setId(productExisting.getId());
                    this.repository.save(product);
                    return productExisting;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    public Optional<Product> findById(Integer id) {
        return repository.findById(id);
    }


    public void delete(Product product) {
        if (product == null || product.getId() == null)
            throw new IllegalArgumentException("Product invalid");
        repository.delete(product);
    }

    public List<Product> findAll() {
        return repository.findAll();

    }

    public List<Product> findByFilter(String q, Double minPrice, Double maxPrice) {
        Specification<Product> specification = Specification
                .where(new FilterNameDescription(q))
                .and(new FilterMinPrice(minPrice))
                .and(new FilterMaxPrice(maxPrice));
        return repository.findAll(specification);
    }

}
