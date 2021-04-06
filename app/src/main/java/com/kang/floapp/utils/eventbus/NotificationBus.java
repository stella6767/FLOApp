package com.kang.floapp.utils.eventbus;

import lombok.Data;

@Data
public class NotificationBus {
    private int playOrNext;  //-1,0,1

    public NotificationBus(int playOrNext) {
        this.playOrNext = playOrNext;
    }
}
