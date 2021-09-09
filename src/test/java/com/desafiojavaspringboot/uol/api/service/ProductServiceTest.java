package com.desafiojavaspringboot.uol.api.service;

import com.desafiojavaspringboot.uol.api.model.Product;
import com.desafiojavaspringboot.uol.api.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Teste da camada service")
class ProductServiceTest {

    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository repository;

    @Test
    void findAll() {

        List<Product> products = Arrays.asList(Product.builder()
                .id(999)
                .description(UUID.randomUUID().toString())
                .name(UUID.randomUUID().toString())
                .price(150.00)
                .build());

        when(repository.findAll()).thenReturn(products);

        List<Product> productList = service.findAll();

        assertThat(productList.size())
                .isNotNull()
                .isEqualTo(1);

        assertThat(productList.get(0).getId())
                .isNotNull();

        assertThat(productList.get(0).getPrice())
                .isEqualTo(products.get(0).getPrice());
    }

}