package com.joongoprime.backend.service;

import com.joongoprime.backend.entity.*;
import com.joongoprime.backend.entity.form.Forms;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final ProductsRepository productsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public ProductService(ProductsRepository productsRepository, UsersRepository usersRepository){
        this.productsRepository = productsRepository;
        this.usersRepository = usersRepository;
    }

    public Products create(Products product) {
        return productsRepository.save(product);
    }

    public Optional<Products> read(Integer pid) {
        return productsRepository.findById(pid);
    }

    public List<Products> load() {return productsRepository.findAll();}

    public DefaultResponse productInfo(Integer pid){
        Optional<Products> product = read(pid);
        if(!product.isPresent()){
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PRODUCT);
        }
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.READ_PRODUCT, product.get());
    }

    public DefaultResponse productShow(){
        List<Products> products = load();
        if(products.isEmpty()){
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PRODUCT_ALL);
        }
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.READ_PRODUCT_ALL, products);
    }

    public DefaultResponse myProductList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.TOKEN_FAILED);
        }
        if (authentication.getName().equals("anonymousUser")){
            return DefaultResponse.res(StatusCode.NEED_REFRESH, ResponseMessage.REQUIRES_TOKEN_UPDATE);
        }
        Optional<Users> users = usersRepository.findById(authentication.getName());
        if (!users.isPresent()) {
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        }
        Users user = users.get();
        List<Products> products = productsRepository.getProductsByUid(user.getUid());
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.READ_PRODUCT, products);
    }

}
