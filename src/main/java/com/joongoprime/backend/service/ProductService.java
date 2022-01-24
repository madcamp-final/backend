package com.joongoprime.backend.service;

import com.joongoprime.backend.entity.Products;
import com.joongoprime.backend.entity.ProductsRepository;
import com.joongoprime.backend.entity.form.Forms;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductService(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    public Products create(Products product) {
        return productsRepository.save(product);
    }

    public Optional<Products> read(Integer pid) {
        return productsRepository.findById(pid);
    }


    public DefaultResponse productInfo(Integer pid){
        Optional<Products> product = read(pid);
        if(!product.isPresent()){
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NO_FOUND_PRODUCT);
        }
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.READ_PRODUCT, product.get());
    }


}
