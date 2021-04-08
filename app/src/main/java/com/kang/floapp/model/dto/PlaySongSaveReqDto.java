package com.kang.floapp.model.dto;

import com.kang.floapp.model.Song;
import com.kang.floapp.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaySongSaveReqDto {
    private User user;
    private Song song;
}
