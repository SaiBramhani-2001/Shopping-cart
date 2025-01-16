package com.focus.Shopping.cart.service.category;

import com.focus.Shopping.cart.model.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;


public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);

}
