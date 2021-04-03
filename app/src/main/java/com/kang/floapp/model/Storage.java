package com.kang.floapp.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Storage {
    private Integer id;
    private String title;
    private String createDate;  //TimeStamp json parser 에러 땜에 못 쓰겠음.
}
