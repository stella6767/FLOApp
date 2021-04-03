package com.kang.floapp.view.common;

import com.kang.floapp.model.User;

public class Constants {
    public static final String BASEURL = "http://172.17.116.44:8080/";
    public static final String FILEPATH = "songlist/";
    public static int isPlaying = -1;
    public static boolean threadStatus = false;
    public static int isRepeat = -1;
    public static int prevNext;
    public static User user = null; //이게 의미가 없는게 앱 종료되면 이 static 변수도 null로 되는 거 아님? 로그인 계속 유지할려면 결국 안 써야겠네..

}

