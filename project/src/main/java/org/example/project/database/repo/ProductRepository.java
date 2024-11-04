package org.example.project.database.repo;

import org.example.project.database.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    Product findById(int id);
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Product> findItemsByNameContainingIgnoreCase(String query, Pageable pageable);
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Query("SELECT COUNT(p) FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    long countProductsByNameContainingIgnoreCase(String query);
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY p.cost")
    Page<Product> findProductsByNameContainingIgnoreCaseSortedByCost(String query, Pageable pageable);
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY p.cost DESC")
    Page<Product> findProductsByNameContainingIgnoreCaseSortedByCostDesc(String query, Pageable pageable);
}
