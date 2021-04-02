package com.kang.floapp.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StorageSong {

    private int id;
    private User user;
    private Storage storage;
    private Song song;
    private Timestamp createDate;

}
