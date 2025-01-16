package com.focus.Shopping.cart.request;

import com.focus.Shopping.cart.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {

    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Integer inventory;
    private Category category;
}
