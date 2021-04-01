package com.kang.floapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthLoginRespDto { //서버로부터 로그인 성공후 응답받은 유저 객체
    private int id;
    private String username;
    private String email;
    private String role;
}
