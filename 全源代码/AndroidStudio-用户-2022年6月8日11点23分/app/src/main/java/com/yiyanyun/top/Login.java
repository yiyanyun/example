package com.yiyanyun.top;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private EditText username;
    EditText password;
    Button login;
    TextView register;

    //这是API接口
    String api = "http://172.17.204.3/api/6Jf3V67PoT";
    //这是AES的密钥【但是本次程序中未使用到AES(其实也就找不到相应的加解密代码罢了。。。。)】
    String AES_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initEvent() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开始处理参数
                String parameter = "user=" + username + "&password=" + password + "&mac=" + library.get_mac() + "&time=" + library.get_time;
                String data = Base64.encodeToString(parameter.getBytes(), Base64.DEFAULT);
                String sign = "&sgin=" + library.md5encryption(data);
                String last = "data=" + data + sign;
                //使用新的线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EasyHttp.post(api, last + sign, new EasyHttp.OnRequestListener() {
                            private JSONObject obj;

                            @Override
                            public void onCompleted(String code, String text, byte[] content, String cookie) {
                                try {
                                    //将返回的结果进行BASE64解码【其实如果你有aes的加解密代码放在这里也是一样的原理为了方便我就使用base46因为大部分语言都自带有方便快捷】
                                    String decode = new String(Base64.decode(text.getBytes(), Base64.DEFAULT));
                                    //解码完成传递给obj进行解析json
                                    this.obj = new JSONObject(decode);
                                    //判断返回状态代码，200为成功
                                    if (obj.getString("code").equals("200")) {
                                        Toast.makeText(getApplication(), obj.getString("explain"), Toast.LENGTH_LONG).show();
                                        //如果状态代码为200则为验证成功上面来一条提示成功，下面则是跳转main活动
                                        toMain();
                                    } else {
                                        //状态码不为200，将提示所返回来的消息进行提示
                                        Toast.makeText(getApplication(), obj.getString("explain"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                }
                            }

                            @Override
                            public void onFailed(String code, String text, byte[] content) {
                                //此处为非服务器返回的消息存在的问题，可能是网络的原因
                                Toast.makeText(getApplication(), "请求失败 请反馈原因" + text, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onProgressChanged(int value) {
                                // TODO: Implement this method
                            }
                        });
                    }
                });
            }
        });
        //注册点击事件

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mac = library.md5encryption(library.get_mac());
                Toast.makeText(getApplication(), "本机设备码为：" + mac, Toast.LENGTH_LONG).show();
                toRegister();
            }
        });
    }

    private void toRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
    }
}