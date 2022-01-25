package com.joongoprime.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@NoArgsConstructor
@Entity
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String buyer;

    private String seller;

    private Integer product_id;

    private Integer bill;

    private Integer completion;

    private Integer buyer_confirm;

    private Integer seller_confirm;

    @Builder
    public Trade (Integer id, String buyer, String seller, Integer product_id, Integer bill, Integer completion, Integer buyer_confirm, Integer seller_confirm){
        this.id = id;
        this.buyer = buyer;
        this.seller = seller;
        this.product_id = product_id;
        this.bill = bill;
        this.completion = completion;
        this.buyer_confirm = buyer_confirm;
        this.seller_confirm = seller_confirm;
    }

    public int setSeller_confirm(int confirmation){
        this.seller_confirm = confirmation;
        return this.seller_confirm;
    }

    public int setBuyer_confirm(int confirmation){
        this.buyer_confirm = confirmation;
        return this.buyer_confirm;
    }

    public int setCompletion (int completion){
        this.completion = completion;
        return this.completion;
    }
}
