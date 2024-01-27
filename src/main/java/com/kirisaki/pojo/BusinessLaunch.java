package com.kirisaki.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * 业务投放对象
 */
@Data
@Entity
@Table(name = "business_launch")
public class BusinessLaunch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, name = "business_detail")
    private String businessDetail;
    @Column(nullable = false, name = "target_city")
    private String targetCity;
    @Column(nullable = false, name = "target_sex")
    private String targetSex;
    @Column(nullable = false, name = "target_product")
    private String targetProduct;
}
