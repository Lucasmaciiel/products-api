package com.desafiojavaspringboot.uol.api.controller;

import com.desafiojavaspringboot.uol.api.model.Product;
import com.desafiojavaspringboot.uol.api.service.ProductService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Teste da camada resource")
class ProductControllerIT {

    @LocalServerPort
    private int port;

    @MockBean
    private ProductService service;

    //Roda antes de cada teste
    @BeforeEach
    void setUp(){

        //habilita os logs quando ocorrer falha
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/products";
    }

    @Test
    @DisplayName("Salva produto com sucesso")
    void shouldSaveProductSuccessfullyStatus201() {
        given()
                    .body("{ \"name\": \"Relógio\", " +
                        "\"description\": \"SmartWath\", " +
                        "\"price\": \"10\" }")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Atualiza produto com sucesso")
    void shouldUpdateProductSuccessfullyStatus200() {

        given()
                    .body("{ \"name\": \"update\", " +
                            "\"description\": \"update2\", " +
                            "\"price\": \"10\" }")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/{id}", 1)
                .then()
                    .statusCode(HttpStatus.OK.value())
                .body("name", is("update"))
                .body("description", is("update2"));
    }

    @Test
    @DisplayName("Deve deletar um produto")
    void shouldDeleteProductSuccessfully(){
        when(this.service.findById(1)).thenReturn(Optional.of(
                Product.builder()
                        .name("Furadeira")
                        .description("furadeira eletrica")
                        .build()));

        given()
                    .contentType(ContentType.JSON)
                .when()
                    .delete("/{id}", 1)
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deve retornar status 200 quando consultar produtos com sucesso")
    void deveReturnStatus200_WhenSearchProducts() {

        given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deve conter quantidade de produtos correta")
    void shouldContain3Products_WhenSearch() {

        when(service.findAll()).thenReturn(Arrays.asList(
                Product.builder()
                        .name("teste")
                        .description("teste")
                        .build(),
                Product.builder()
                        .name("teste2")
                        .description("teste2")
                        .build(),
                Product.builder()
                        .name("teste3")
                        .description("teste3")
                        .build()));

        given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", hasSize(3))
                    .body("name", hasItems("teste", "teste2", "teste3"));
    }

    @Test
    @DisplayName("Deve retornar um produto")
    void shouldReturnProduct_WhenPassedId(){

        when(service.findById(999)).thenReturn(Optional.ofNullable(
                Product.builder()
                        .name("teste")
                        .description("teste")
                        .build()));

        given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/{id}", 999)
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Não deve retornar produto, quando não encontrar")
    void shouldNotReturnProduct(){

        when(service.findById(1)).thenReturn(Optional.empty());

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/{id}", 1)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}