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
public class Prefer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String user_id;
    private Integer product_id;

    @Builder
    public Prefer (String user_id, Integer product_id) {
        this.user_id = user_id;
        this.product_id = product_id;
    }
}
