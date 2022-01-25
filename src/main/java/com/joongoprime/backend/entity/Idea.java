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
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;
    private String category;
    private String content;

    @Builder
    public Idea (Integer id, String type, String category, String content){
        this.id = id;
        this.type = type;
        this.category = category;
        this.content = content;
    }
}
