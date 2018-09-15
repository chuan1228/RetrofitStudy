package com.chuan_sir.retrofitstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chuan_sir.retrofitstudy.internet.GetRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化使用
        GetRequest.getInstance().init(getApplicationContext());
        //使用单例调用实例方法，如下
//        GetRequest.getInstance().postBackObject();
    }
}
