package com.codegym.service.product;

import com.codegym.exception.DataInputException;
import com.codegym.model.Product;
import com.codegym.model.ProductAvatar;
import com.codegym.model.dto.productDTO.ProductCreateDTO;
import com.codegym.model.dto.productDTO.ProductDTO;
import com.codegym.model.enums.FileType;
import com.codegym.repository.ProductRepository;
import com.codegym.service.productAvatar.ProductAvatarServiceImpl;
import com.codegym.service.upload.IUploadService;
import com.codegym.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductAvatarServiceImpl productAvatarService;

    @Autowired
    IUploadService iUploadService;

    @Autowired
    UploadUtil uploadUtil;

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public List<ProductDTO> findAllByDeletedIsFalse() {
        return productRepository.findAllByDeletedIsFalse();
    }

    @Override
    public boolean existsByNameProduct(String nameProduct) {
        return productRepository.existsByNameProduct(nameProduct);
    }

    @Override
    public Boolean existsByNameProductAndIdNot(String productName, Long id) {
        return productRepository.existsByNameProductAndIdNot(productName,id);
    }


    @Override
    public Product createWithAvatar(ProductCreateDTO productCreateDTO) {
        MultipartFile file = productCreateDTO.getFile();
        String fileType = file.getContentType();
        assert fileType != null;
        fileType = fileType.substring(0, 5);

        ProductAvatar productAvatar = new ProductAvatar();
        productAvatar.setFileType(fileType);
        productAvatar = productAvatarService.save(productAvatar);

        if (fileType.equals(FileType.IMAGE.getValue())) {
            productAvatar = uploadAndSaveProductAvatar(file, productAvatar);
        }
        Product product = productCreateDTO.toProduct(productAvatar);
        product = productRepository.save(product);
        return product;
    }

    @Override
    public Product saveWithAvatar(Product product, MultipartFile file) {
        ProductAvatar oldProductAvatar = product.getProductAvatar();
        try {
            iUploadService.destroyImage(oldProductAvatar.getCloudId(), uploadUtil.buildImageDestroyParams(product, oldProductAvatar.getCloudId()));
            productAvatarService.delete(oldProductAvatar.getId());
            String fileType = file.getContentType();
            assert fileType != null;
            fileType = fileType.substring(0, 5);
            ProductAvatar productAvatar = new ProductAvatar();
            productAvatar.setFileType(fileType);
            productAvatar = productAvatarService.save(productAvatar);

            if (fileType.equals(FileType.IMAGE.getValue())) {
                productAvatar = uploadAndSaveProductAvatar(file, productAvatar);
            }
            product.setProductAvatar(productAvatar);
            product = productRepository.save(product);
            return product;
        } catch (IOException e) {
            throw new DataInputException("Xóa hình ảnh thất bại.");
        }
    }


    private ProductAvatar uploadAndSaveProductAvatar(MultipartFile file, ProductAvatar productAvatar) {
        try {
            Map uploadResult = iUploadService.uploadImage(file, uploadUtil.buildImageUploadParams(productAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productAvatar.setFileName(productAvatar.getId() + "." + fileFormat);
            productAvatar.setFileUrl(fileUrl);
            productAvatar.setFileFolder(UploadUtil.PRODUCT_IMAGE_UPLOAD_FOLDER);
            productAvatar.setCloudId(productAvatar.getFileFolder() + "/" + productAvatar.getId());
            return productAvatarService.save(productAvatar);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại.");
        }
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void removeById(Long id) {
    }

    @Override
    public void remove(Product product) {

    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void softDelete(Long productId) {
        productRepository.softDelete(productId);
    }
}
