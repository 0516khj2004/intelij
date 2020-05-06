package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"}) -> 개별적으로 하지 않고!
@JsonFilter("UserInfo")
public class User {
    private Integer id;
    private String name;
    private Date localDate;
//    @JsonIgnore // 시용자로부터 보여주고 싶지 않은 데이터 - 외부에 공개하지 않음
    private String password;
//    @JsonIgnore
    private String ssn;
}
