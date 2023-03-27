package com.codegym.api;


import com.codegym.exception.DataInputException;
import com.codegym.exception.EmailExistsException;
import com.codegym.model.Product;
import com.codegym.model.dto.productDTO.ProductCreateDTO;
import com.codegym.model.dto.productDTO.ProductDTO;
import com.codegym.model.dto.productDTO.ProductUpdateDTO;
import com.codegym.service.product.IProductService;
import com.codegym.service.product.ProductServiceImpl;
import com.codegym.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {

    @Autowired
    IProductService iProductService;

    @Autowired
    AppUtils appUtils;

    @Autowired
    ProductServiceImpl productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        List<ProductDTO> productDTOS = iProductService.findAllByDeletedIsFalse();
        if (productDTOS.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getById(@PathVariable Long productId) {

        Optional<Product> optionalProduct = iProductService.findById(productId);

        if (!optionalProduct.isPresent()) {
            throw new DataInputException("ID hàng hóa không hợp lệ !");
        }

        Product product = optionalProduct.get();
        ProductDTO productDTO = product.toProductDTO();
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> create(@Validated ProductCreateDTO productCreateDTO, BindingResult bindingResult) {
        new ProductCreateDTO().validate(productCreateDTO, bindingResult);
        MultipartFile imageFile = productCreateDTO.getFile();

        if (imageFile == null) {
            throw new DataInputException("Vui lòng chọn hình ảnh!!");
        }

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        if (productService.existsByNameProduct(productCreateDTO.getNameProduct())) {
            throw new EmailExistsException("Sản phẩm đã tồn tại trong hệ thống!!");
        }

        productCreateDTO.setId(null);
        Product newProduct = productService.createWithAvatar(productCreateDTO);

        return new ResponseEntity<>(newProduct.toProductDTO(), HttpStatus.CREATED);
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long productId, MultipartFile file, @Validated ProductUpdateDTO productUpdateDTO, BindingResult bindingResult) {
        Optional<Product> productOptional = productService.findById(productId);
        if (!productOptional.isPresent()) {
            throw new DataInputException("ID sản phẩm không tồn tại!!");
        }

        Product product = productOptional.get();

        if (productService.existsByNameProductAndIdNot(productUpdateDTO.getNameProduct(), productId)) {
            throw new DataInputException("Sản phẩm đã tồn tại trong hệ thống!!");
        }

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        String productName = productUpdateDTO.getNameProduct();
        BigDecimal price = new BigDecimal(Long.parseLong(productUpdateDTO.getPrice()));
        int quantity = Integer.parseInt(productUpdateDTO.getQuantity());
        String description = productUpdateDTO.getDescription();

        product.setNameProduct(productName)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description);
        product = productService.save(product);

        if(file != null){
            product = productService.saveWithAvatar(product, file);
        }
        return new ResponseEntity<>(product.toProductDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long productId) {

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new DataInputException("ID sản phẩm không hợp lệ!!");
        }

        try {
            productService.softDelete(productId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new DataInputException("Vui lòng liên hệ Administrator!!");
        }
    }
}
