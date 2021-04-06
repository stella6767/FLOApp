package com.kang.floapp.model;

import com.kang.floapp.model.Song;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaySong { //내 재생목록

    private Integer id;
    private Song song;
    private String createDate;

}
