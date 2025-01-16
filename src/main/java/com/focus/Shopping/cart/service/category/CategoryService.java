package com.focus.Shopping.cart.service.category;

import com.focus.Shopping.cart.exceptions.AlreadyExistsException;
import com.focus.Shopping.cart.exceptions.ResourceNotFoundException;
import com.focus.Shopping.cart.model.Category;
import com.focus.Shopping.cart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> categoryRepository.existsByName(c.getName()))
                .map(categoryRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + "Already exists"));
    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> {throw new ResourceNotFoundException("Category Not Found");});

    }
}
