package com.joongoprime.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor //해당 클래스의 기본 생성자를 생성해주는 annotation
@Entity
public class Users {

    @Id //해당 member(uid)가 entity(Users)의 pk임을 의미함
    private String uid; //uid varchar(50)

    private String user_name; //user_name varchar(50)

    private String password; //password varchar(100)

    private String account_type; //account_type varchar(10) check ( account_type in ('LOCAL', 'KAKAO') ) not null

    private int points; //account_type varchar(10) check ( account_type in ('LOCAL', 'KAKAO') ) not null

    @Builder
    public Users(String uid, String user_name, String password, String account_type, int points){
        this.uid = uid;
        this.user_name = user_name;
        this.password = password;
        this.account_type = account_type;
        this.points = points;
    }

}
