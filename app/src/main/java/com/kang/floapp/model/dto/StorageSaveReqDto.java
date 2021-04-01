package com.kang.floapp.model.dto;

import com.kang.floapp.model.Storage;

import lombok.Data;

@Data
public class StorageSaveReqDto {
    private String title;

    public Storage toEntity(){
        Storage storage = new Storage().builder()
                .title(title)
                .build();
        return storage;
    }
}
