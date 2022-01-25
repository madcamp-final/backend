package com.joongoprime.backend.controller;

import com.joongoprime.backend.entity.form.Forms;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.service.TransactionService;
import com.joongoprime.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/prefer")
    public DefaultResponse preferProduct(@RequestBody Forms.PreferForm preferForm){
        return transactionService.preferProduct(preferForm.getUid(), preferForm.getProduct_id());
    }


}
