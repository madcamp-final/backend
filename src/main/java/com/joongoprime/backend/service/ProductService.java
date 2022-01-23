package com.joongoprime.backend.service;

import com.joongoprime.backend.entity.Products;
import com.joongoprime.backend.entity.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
