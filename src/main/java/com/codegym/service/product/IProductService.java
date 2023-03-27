package com.codegym.service.product;

import com.codegym.model.Product;
import com.codegym.model.dto.productDTO.ProductCreateDTO;
import com.codegym.model.dto.productDTO.ProductDTO;
import com.codegym.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService extends IGeneralService<Product> {
    List<ProductDTO> findAllByDeletedIsFalse();

    boolean existsByNameProduct(String nameProduct);

    Product createWithAvatar(ProductCreateDTO productCreateDTO);
    Boolean existsByNameProductAndIdNot(String productName, Long id);
    Product saveWithAvatar(Product product, MultipartFile file);

    void softDelete(Long productId);
}
