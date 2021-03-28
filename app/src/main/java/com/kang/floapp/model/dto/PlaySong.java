package com.kang.floapp.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaySong {

    private Integer id;
    private Song song;
    private Timestamp createDate;

}
