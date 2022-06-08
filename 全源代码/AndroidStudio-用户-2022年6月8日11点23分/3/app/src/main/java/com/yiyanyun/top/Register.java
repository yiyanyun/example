package com.yiyanyun.top;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private Button get_code;
    private Button submit;
    private EditText UserName;
    EditText PassWord;
    EditText mail;
    EditText code;
    EditText NickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }

    //事件开始
    private void initEvent() {
        get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取验证码按钮点击事件
                get_code();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                提交点击事件
            }
        });
    }

    private void get_code() {
        Toast.makeText(getApplicationContext(), "you onclick get code button", Toast.LENGTH_LONG).show();
    }

    // 获取控件的ID
    private void initView() {
        get_code = findViewById(R.id.get_code);
        UserName = findViewById(R.id.username);
        PassWord = findViewById(R.id.password);
        NickName = findViewById(R.id.nickname);
        mail     = findViewById(R.id.mail);
        submit   = findViewById(R.id.submit);
    }
}