package com.kirisaki.repo;

import com.kirisaki.pojo.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
    public Products findByProductId(String productId);
}
