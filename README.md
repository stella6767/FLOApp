# FLO 앱(클론 코딩)


### 간단설명

바인딩 서비스를 이용한 간단한 음악스트리밍어플

![image](https://user-images.githubusercontent.com/65489223/114034526-39248b00-98b9-11eb-9dac-d0d79425b0ae.png)
![image](https://user-images.githubusercontent.com/65489223/114035629-3c6c4680-98ba-11eb-8e2b-fff8fac5e43b.png)
![image](https://user-images.githubusercontent.com/65489223/114035669-47bf7200-98ba-11eb-80c3-e988b36856a2.png)
![image](https://user-images.githubusercontent.com/65489223/114036122-c0263300-98ba-11eb-80c0-2cff909c1b05.png)
![image](https://user-images.githubusercontent.com/65489223/114036167-cd432200-98ba-11eb-8ca0-9f55a84480d1.png)
![image](https://user-images.githubusercontent.com/65489223/114036233-dcc26b00-98ba-11eb-9754-7075da961e15.png)
![image](https://user-images.githubusercontent.com/65489223/114036270-e51aa600-98ba-11eb-9ae9-25f9bcaeee8c.png)
![image](https://user-images.githubusercontent.com/65489223/114036364-f9f73980-98ba-11eb-8096-794ddab76c7a.png)
![image](https://user-images.githubusercontent.com/65489223/114036332-f2d02b80-98ba-11eb-94cd-f67f4ccb6a33.png)
![image](https://user-images.githubusercontent.com/65489223/114036459-0e3b3680-98bb-11eb-8130-b0d7cbcc5533.png)
![image](https://user-images.githubusercontent.com/65489223/114036563-23b06080-98bb-11eb-8fc2-0c92e41665f9.png)
![image](https://user-images.githubusercontent.com/65489223/114035570-2f4f5780-98ba-11eb-8d49-842ef0ab1ee0.png)




### 연동서버 

스프링부트 RESTAPI서버: [https://github.com/stella6767/FLOApp-API-SERVER]



### 의존성

``` 
dependencies {
    def lifecycle_version = "2.2.0";
    implementation "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version"
    compileOnly 'org.projectlombok:lombok:1.18.10'
    annotationProcessor 'org.projectlombok:lombok:1.18.10'
    implementation 'org.greenrobot:eventbus:3.2.0'
    implementation 'com.squareup.retrofit2:retrofit:2.6.2'
    implementation 'com.squareup.retrofit2:converter-gson:2.1.0'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    implementation 'de.hdodenhof:circleimageview:3.1.0'
    implementation 'com.github.gmazzo:fontawesome:0.4'
    implementation 'com.makeramen:roundedimageview:2.3.0'
    implementation 'com.sothree.slidinguppanel:library:3.4.0'
    implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'gr.pantrif:easy-android-splash-screen:0.0.1'
    
    ----- 여기까지 추가 의존성 --------

    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
}
```

```AndroidManifes.xml 설정
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.kang.floapp">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@drawable/flo_logo"
        android:label="@string/app_name"
        android:roundIcon="@drawable/flo_logo"
        android:supportsRtl="true"
        android:theme="@style/Theme.FloApp"
        android:usesCleartextTraffic="true"
        android:windowSoftInputMode="adjustResize">
        <receiver
            android:name=".utils.notification.NotificationActionService"
            android:enabled="true"
            android:exported="true"></receiver>

        <activity android:name=".view.profile.ProfileUpdateActivity" />
        <activity android:name=".view.profile.ProfileActivity" />
        <activity android:name=".view.user.JoinActivity" />
        <activity
            android:name=".view.user.LoginActivity"
            android:windowSoftInputMode="adjustResize" />

        <service
            android:name=".utils.PlayService"
            android:enabled="true"
            android:exported="true" />

        <activity android:name=".view.main.MainActivity" />
        <activity android:name=".SplashActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```



### 모델

- Song
- Category
- PlaySong
- Storage
- StorageSong
- User


![image](https://user-images.githubusercontent.com/65489223/114033215-05953100-98b8-11eb-8e66-dd0f63d45302.png)

Reply는 웹 서버와 연동을 위해서 관계가 있을 뿐, 앱에서는 사용을 안함.



### MVVM 패턴

```라이브 데이터
    private MutableLiveData<List<Song>> mtSongList;
    private MutableLiveData<List<PlaySong>> mtPlayList;
    private MutableLiveData<List<Song>> mtSearchSongList;
    private MutableLiveData<List<Storage>> mtStorageList;
    private MutableLiveData<List<StorageSong>> mtStorageSongList;
    private MutableLiveData<List<Song>> mtCategoryList;
    private MutableLiveData<PlayService.LocalBinder> serviceBinder 

```





### 블로그 주소




### 동영상 주소





### PPT 주소





