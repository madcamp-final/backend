package com.joongoprime.backend.controller;

import com.joongoprime.backend.entity.Products;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import com.joongoprime.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
