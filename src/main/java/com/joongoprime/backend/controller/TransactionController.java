package com.joongoprime.backend.controller;

import com.joongoprime.backend.entity.form.Forms;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.service.TransactionService;
import com.joongoprime.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/prefer-list")
    public DefaultResponse getPreferList(@RequestParam("pid") Integer pid) {
        return transactionService.getPreferList(pid);
    }

    @PostMapping("/start-trade")
    public DefaultResponse startTrade(@RequestBody Forms.PreferForm preferForm){
        return transactionService.startTrade(preferForm);
    }

    @GetMapping("/accept")
    public DefaultResponse acceptTrade(@RequestParam("tid") Integer tid){
        return transactionService.acceptTrade(tid);
    }
}
