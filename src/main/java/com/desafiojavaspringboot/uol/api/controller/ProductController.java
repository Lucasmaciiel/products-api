package com.desafiojavaspringboot.uol.api.controller;

import com.desafiojavaspringboot.uol.api.model.Product;
import com.desafiojavaspringboot.uol.api.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

    private final ProductService service;

    @ApiOperation(value = "Create a product")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Returns when a product is successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<Product> save(@Valid @RequestBody Product product) {
        this.service.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Updates a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the product was successfully updated"),
            @ApiResponse(code = 404, message = "Returns when the product does not exist"),
            @ApiResponse(code = 400, message = "Validation error"),

    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> update(@PathVariable("id") Integer id, @Valid @RequestBody Product product) {
        this.service.updateProduct(id, product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @ApiOperation(value = "Search for a product by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the product was found successfully"),
            @ApiResponse(code = 404, message = "Returns when the product does not exist")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Integer id) {
        Optional<Product> product = this.service.findById(id);
        return product.map(value ->
                        new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the product is successfully deleted"),
            @ApiResponse(code = 404, message = "Returns when the product does not exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Optional<Product> product = this.service.findById(id);
        return product.map(value -> {
            this.service.delete(value);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Product list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the products were found successfully or the product list is empty")
    })
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        var products = this.service.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @ApiOperation(value = "Filter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Product>> findByFilter(
                                                    @RequestParam(required = false) String q,
                                                    @RequestParam(required = false) Double minPrice,
                                                    @RequestParam(required = false) Double maxPrice) {

        var products = this.service.findByFilter(q, minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
