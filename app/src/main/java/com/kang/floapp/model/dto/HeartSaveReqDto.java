package com.kang.floapp.model.dto;

import com.kang.floapp.model.Song;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeartSaveReqDto {

    private Song song;
}
