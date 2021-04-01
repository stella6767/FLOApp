package com.kang.floapp.model.dto;

import com.kang.floapp.model.Song;
import com.kang.floapp.model.Storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StorageSongSaveReqDto {
    private Storage storage;
    private Song song;
}
