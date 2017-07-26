package com.example.linzongzhan.sellerclient;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";

    private CustomApplication app;

    private MyService.ServiceWay mServiceWay;

    private IntentFilter intentFilter;

    private MyBroadcastReceiver myBroadcastReceiver;

    private EditText user;

    private EditText password;

    private Button signin;

    private Button signup;

    private String name;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceWay = (MyService.ServiceWay) iBinder;
            mServiceWay.log();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity_layout);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);//登录
        signup = (Button) findViewById(R.id.signup);//注册
        user.setOnClickListener(this);
        password.setOnClickListener(this);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);

        Intent serviceIntent = new Intent(this,MyService.class);
        startService (serviceIntent);
        bindService(serviceIntent,connection,BIND_AUTO_CREATE);
        Log.d(TAG, "onCreate: 666666");




        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttext.get");
        myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver,intentFilter);

        app = (CustomApplication) getApplication();
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user:

                break;
            case R.id.password:

                break;
            case R.id.signin:

                mServiceWay.listen();


                Command command = new Command();
                command.setName(user.getText().toString());
                name = user.getText().toString();
                command.setPassword(password.getText().toString());
                command.setOrder("signIn");
                mServiceWay.sendMessage(command);
                break;
            case R.id.signup:

                mServiceWay.listen();

                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        Intent serviceIntent = new Intent(this,MyService.class);
        unbindService(connection);
        stopService(serviceIntent);

        unregisterReceiver(myBroadcastReceiver);
    }

    private class MyBroadcastReceiver extends android.content.BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //改变view的逻辑
            System.out.println("666");
            Guide guide = app.getGuide();
            if (guide == null) {

            } else {
                String order = guide.getOrder();
                if (order.equals("signin")) {
                    //如果获得正确的信息，则将队列中的信息进行出列
                    app.pollGuide();

                    int result = guide.getResult();
                    if (result == 1) {
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT);
                        app.setName(name);
                        //开启主界面
                        Intent intent1 = new Intent(LoginActivity.this,InterfaceActivity.class);
                        startActivity(intent1);

                    } else if (result == 0) {
                        Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT);
                    }
                }
                if (order.equals("newOrder")) {
                    app.pollGuide();

                    Toast.makeText(LoginActivity.this,"你有一个新订单等待处理",Toast.LENGTH_LONG);
                }
            }
        }
    }
}
