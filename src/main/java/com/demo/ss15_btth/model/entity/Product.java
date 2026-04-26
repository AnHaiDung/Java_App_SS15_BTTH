package com.demo.ss15_btth.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String category;
    private Boolean status;
}