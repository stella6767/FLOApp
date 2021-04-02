package com.kang.floapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {  //서버로부터 받은 유저 객체
    private int id;
    private String username;
    private String email;
    private String role;
}
