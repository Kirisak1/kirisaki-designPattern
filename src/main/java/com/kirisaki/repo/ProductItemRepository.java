package com.kirisaki.repo;

import com.kirisaki.pojo.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem,Integer> {

}
