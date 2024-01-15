package com.kirisaki.pojo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_item")
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int pid;
}
