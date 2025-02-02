package com.focus.Shopping.cart.controller;


import com.focus.Shopping.cart.dto.ProductDto;
import com.focus.Shopping.cart.exceptions.ResourceNotFoundException;
import com.focus.Shopping.cart.model.Product;
import com.focus.Shopping.cart.request.AddProductRequest;
import com.focus.Shopping.cart.request.ProductUpdateRequest;
import com.focus.Shopping.cart.response.ApiResponse;
import com.focus.Shopping.cart.service.product.IProductService;
import com.focus.Shopping.cart.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {

    private final IProductService productService;



    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("success", productDto));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("successfully added", theProduct));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, Long id) {
        try {
                Product theProduct = productService.updateProduct(request,id);
                return ResponseEntity.ok(new ApiResponse("successfully updated", theProduct));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("successfully deleted", id));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand,productName);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
//            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
//            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }
    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
//            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
//            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }


}
