package com.yiyanyun.top;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {
    private Button skip;
    private Handler handler = new Handler();
    private Handler ehandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            toLogin();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
        initEvent();
        ehandler.postDelayed(runnable,3000);
    }

    private void initEvent() {
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLogin();
                handler.removeCallbacks(runnable);
            }
        });
    }

    private void initView() {
        skip = findViewById(R.id.skip);
    }

    private void toRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void toLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}