package com.desafiojavaspringboot.uol.api.service;

import com.desafiojavaspringboot.uol.api.model.Product;
import com.desafiojavaspringboot.uol.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product save(Product product) {
        return repository.save(product);
    }

    @Transactional
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


}
