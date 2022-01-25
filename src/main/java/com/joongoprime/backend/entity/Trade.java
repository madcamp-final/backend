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

    private Byte completion;

    private Byte buyer_confirm;

    private Byte seller_confirm;

    @Builder
    public Trade (Integer id, String buyer, String seller, Integer product_id, Integer bill, Byte completion, Byte buyer_confirm, Byte seller_confirm){
        this.id = id;
        this.buyer = buyer;
        this.seller = seller;
        this.product_id = product_id;
        this.bill = bill;
        this.completion = completion;
        this.buyer_confirm = buyer_confirm;
        this.seller_confirm = seller_confirm;
    }
}
