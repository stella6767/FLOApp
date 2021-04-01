package com.kang.floapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Song {
    private Integer id;
    private String title;
    private String artist;
    private String category;
    private Integer duration;
    private String lyrics;
    private String date;
    private String img;
    //private List<> heart
    private String file;
}
