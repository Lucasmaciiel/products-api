package com.desafiojavaspringboot.uol.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;


@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Field name is required")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "Field description is required")
    private String description;

    @Column(name = "price", precision = 20, scale = 2)
    @NotNull(message = "Field price is required")
    @Positive(message = "Field price must be positive")
    private Double price;


}