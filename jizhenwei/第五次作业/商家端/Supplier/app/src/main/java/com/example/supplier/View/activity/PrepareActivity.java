package com.example.supplier.View.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.supplier.R;
import com.example.supplier.View.frag.LoginFrag;
import com.example.supplier.View.frag.RegisterFrag;

public class PrepareActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    LoginFrag loginFrag = new LoginFrag();
    RegisterFrag registerFrag = new RegisterFrag();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);
        initFrag();
        constraintLayout = (ConstraintLayout) findViewById(R.id.prepare_frag_container);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.window_in, R.anim.window_out);
        transaction.add(R.id.prepare_frag_container,loginFrag);
        transaction.commit();
    }
    private void initFrag(){
        loginFrag.setOnSwitchRegisterListener(new LoginFrag.OnSwitchRegisterListener() {
            @Override
            public void onSwitchRegister() {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.window_in, R.anim.window_out);
                transaction.replace(R.id.prepare_frag_container,registerFrag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
