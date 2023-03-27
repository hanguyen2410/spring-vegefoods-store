package com.codegym.model;

import com.codegym.model.dto.productDTO.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameProduct;


    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price;

    private int  quantity;

    private String description;

    @OneToOne
    @JoinColumn(name = "product_avatar_id")
    private ProductAvatar productAvatar;

    public ProductDTO toProductDTO() {
        return new ProductDTO()
                .setId(id)
                .setNameProduct(nameProduct)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description)
                .setProductAvatar(productAvatar.toProductAvatarDTO())
                ;
    }

}
