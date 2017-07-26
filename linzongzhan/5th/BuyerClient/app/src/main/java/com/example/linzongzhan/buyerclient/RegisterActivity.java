package com.example.linzongzhan.buyerclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private CustomApplication app;

    private MyService.ServiceWay mServiceWay;

    private IntentFilter intentFilter;

    private RegisterActivity.MyBroadcastReceiver myBroadcastReceiver;

    private EditText user;

    private EditText password;

    private Button register;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceWay = (MyService.ServiceWay) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeractivity_layout);
        user = (EditText) findViewById(R.id.register_user);
        password = (EditText) findViewById(R.id.register_password);
        register = (Button) findViewById(R.id.register_register);
        register.setOnClickListener(this);

        Intent serviceIntent = new Intent(this,MyService.class);
        startService (serviceIntent);
        bindService(serviceIntent,connection,BIND_AUTO_CREATE);

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttext.get");
        myBroadcastReceiver = new RegisterActivity.MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver,intentFilter);

        app = (CustomApplication) getApplication();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_register:
                Command command = new Command();
                command.setName(user.getText().toString());
                command.setPassword(password.getText().toString());
                command.setOrder("signUp");
                mServiceWay.sendMessage(command);
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        unbindService(connection);

        unregisterReceiver(myBroadcastReceiver);
    }

    private class MyBroadcastReceiver extends android.content.BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //改变view的逻辑
            Guide guide = app.getGuide();
            String order = guide.getOrder();
            if (order.equals("signup")) {
                app.pollGuide();

                int result = guide.getResult();
                if (result == 1) {
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT);
                    finish();
                } else if (result == 0) {
                    Toast.makeText(RegisterActivity.this,"用户名已存在，请重新输入",Toast.LENGTH_SHORT);
                }
            }
            if (order.equals("newOrder")) {
                app.pollGuide();

                Toast.makeText(RegisterActivity.this,"你有一个新订单等待处理",Toast.LENGTH_LONG);
            }
        }
    }
}
