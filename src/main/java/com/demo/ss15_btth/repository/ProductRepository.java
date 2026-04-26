package com.demo.ss15_btth.repository;

import com.demo.ss15_btth.model.entity.Product;
import com.demo.ss15_btth.model.entity.ProductMobileProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByCategoryAndStatus(String category, Boolean status);

    @Query("SELECT p FROM Product p")
    Page<Product> findAllPagedAndSorted(Pageable pageable);

    @Query("SELECT p.productName as productName, p.price as price FROM Product p")
    List<ProductMobileProjection> findAllForMobile();
}