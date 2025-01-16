package com.focus.Shopping.cart.dto;

import com.focus.Shopping.cart.model.Category;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;


@Data
public class ProductDto {


    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Integer inventory;
    private Category category;
    private List<ImageDto> images;
}
