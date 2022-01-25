package com.joongoprime.backend.controller;

import com.joongoprime.backend.entity.Products;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import com.joongoprime.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public DefaultResponse createProductInfo(@RequestBody Products products){
        Products created = productService.create(products);
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.CREATED_USER, created);
    }

    @GetMapping("/confirm")
    public DefaultResponse confirmProductInfo(@RequestParam Integer pid){
        return productService.productInfo(pid);
    }

    @GetMapping("/load")
    public DefaultResponse loadProductInfo(){
        return productService.productShow();
    }

}
