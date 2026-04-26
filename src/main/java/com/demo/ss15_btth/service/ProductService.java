package com.demo.ss15_btth.service;

import com.demo.ss15_btth.model.entity.Product;
import com.demo.ss15_btth.model.entity.ProductMobileProjection;
import com.demo.ss15_btth.repository.ProductRepository;
import com.demo.ss15_btth.repository.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getMarketingProducts(String category) {
        return productRepository.findByCategoryAndStatus(category, true);
    }

    public Page<Product> getPremiumProducts(int page, int size) {
        if (page < 0) throw new RuntimeException("Trang không được âm");
        return productRepository.findAllPagedAndSorted(PageRequest.of(page, size, Sort.by("price").descending()));
    }

    public List<ProductMobileProjection> getMobileProducts() {
        return productRepository.findAllForMobile();
    }

    public List<Product> searchProducts(String name, Double min, Double max) {
        if (min != null && max != null && min > max) {
            throw new RuntimeException("Giá bắt đầu không được lớn hơn giá kết thúc");
        }
        return productRepository.findAll(Specification.where(ProductSpecification.hasName(name))
                .and(ProductSpecification.hasPriceBetween(min, max)));
    }
}