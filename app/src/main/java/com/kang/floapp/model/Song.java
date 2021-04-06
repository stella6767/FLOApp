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
    private String lyrics;
    private String relaseDate;
    private String img;
    private String file;
}
