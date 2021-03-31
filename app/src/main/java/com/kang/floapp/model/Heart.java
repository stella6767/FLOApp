package com.kang.floapp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Heart {

    private int id;
    private User user;
    private Song song;
    private int heart; //1이면 좋아요, 0이면 아님
}
