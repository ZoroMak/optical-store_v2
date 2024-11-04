package org.example.project.database.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.database.model.Product;
import org.example.project.database.repo.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    @Transactional
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    /**
     * <p>Getting products for a specific page</p>
     * @param pageNumber Page number, number of products per page
     * @param pageSize Number of products per page
     * @return the amount of health hero has after attack
     */
    @Transactional
    public Page<Product> findProductsByPage(int pageNumber, int pageSize, String searchValue) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findItemsByNameContainingIgnoreCase(searchValue, pageable);
    }
    /**
     * <p>Getting products by name</p>
     * @param searchValue product name
     * @return the information about products that match the search parameter
     */
    @Transactional
    public long getTotalProductCount(String searchValue) {
        return productRepository.countProductsByNameContainingIgnoreCase(searchValue);
    }
    @Transactional
    public Page<Product> getSortedProduct(int pageNumber, int pageSize, boolean value, String searchValue){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return value ? productRepository.findProductsByNameContainingIgnoreCaseSortedByCost(searchValue, pageable)
                : productRepository.findProductsByNameContainingIgnoreCaseSortedByCostDesc(searchValue, pageable);
    }
}
