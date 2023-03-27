package com.codegym.repository;

import com.codegym.model.Product;
import com.codegym.model.dto.productDTO.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT NEW com.codegym.model.dto.productDTO.ProductDTO (" +
            "pro.id, " +
            "pro.nameProduct, " +
            "pro.price, " +
            "pro.quantity, " +
            "pro.description," +
            "pro.productAvatar" +
            ") " +
            "FROM Product AS pro " +
            "WHERE pro.deleted = false "
    )
    List<ProductDTO> findAllByDeletedIsFalse();

    boolean existsByNameProduct(String nameProduct);
    Boolean existsByNameProductAndIdNot(String productName, Long id);

    @Modifying
    @Query("UPDATE Product AS p SET p.deleted = true WHERE p.id = :productId")
    void softDelete(@Param("productId") long productId);
}
