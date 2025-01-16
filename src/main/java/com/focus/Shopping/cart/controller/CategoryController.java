package com.focus.Shopping.cart.controller;


import com.focus.Shopping.cart.exceptions.AlreadyExistsException;
import com.focus.Shopping.cart.exceptions.ResourceNotFoundException;
import com.focus.Shopping.cart.model.Category;
import com.focus.Shopping.cart.repository.CategoryRepository;
import com.focus.Shopping.cart.response.ApiResponse;
import com.focus.Shopping.cart.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("found", categories));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/category/addCategory")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try {
            Category category = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("New Category added", category));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{id}/getById")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("found", category));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", null));
        }
    }

    @GetMapping("/category/{name}/getByName")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("found", category));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", null));
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("deleted", null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category){
        try {
            Category updatedCategory = categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("update success", updatedCategory));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }




}
