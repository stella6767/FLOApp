package com.kang.floapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StorageSong {
    private Integer id;
    private Storage storage;
    private Song song;
}
