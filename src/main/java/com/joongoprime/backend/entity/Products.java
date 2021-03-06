package com.joongoprime.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    private String uid;

    private Integer price;

    private String product_type;

    private String images_dir;

    private Byte completion;


    private String des_idea_1;
    private String des_detail_1;

    private String des_idea_2;
    private String des_detail_2;

    private String des_idea_3;
    private String des_detail_3;

    private Integer des_idea_4;
    private String des_detail_4;

    private Integer des_idea_5;
    private String des_detail_5;

    @Builder
    public Products(Integer pid, String uid, Integer price, String product_type, String images_dir, Byte completion, String des_idea_1, String des_detail_1, String des_idea_2, String des_detail_2, String des_idea_3, String des_detail_3, Integer des_idea_4, String des_detail_4, Integer des_idea_5, String des_detail_5){
        this.pid = pid;
        this.uid = uid;
        this.price = price;
        this.product_type = product_type;
        this.images_dir = images_dir;
        this.completion = completion;
        this.des_idea_1 = des_idea_1;
        this.des_detail_1 = des_detail_1;
        this.des_idea_2 = des_idea_2;
        this.des_detail_2 = des_detail_2;
        this.des_idea_3 = des_idea_3;
        this.des_detail_3 = des_detail_3;
        this.des_idea_4 = des_idea_4;
        this.des_detail_4 = des_detail_4;
        this.des_idea_5 = des_idea_5;
        this.des_detail_5 = des_detail_5;
    }

}
