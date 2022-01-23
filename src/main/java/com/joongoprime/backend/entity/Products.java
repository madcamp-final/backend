package com.joongoprime.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Products {

    @Id
    private Integer pid;

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

    private String des_idea_4;
    private String des_detail_4;

    private String des_idea_5;
    private String des_detail_5;


}
