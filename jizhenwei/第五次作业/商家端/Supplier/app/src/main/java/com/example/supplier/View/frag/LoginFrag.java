package com.example.supplier.View.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.supplier.Control.Controler;
import com.example.supplier.Model.Customer;
import com.example.supplier.Model.Supplier;
import com.example.supplier.R;
import com.example.supplier.uitl.Tool;
import com.example.supplier.View.activity.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class LoginFrag extends Fragment implements View.OnClickListener {
    TextInputLayout accountInputLayout, passwordInputLayout;
    Button swichToregister, login;
    Controler controler;

    public interface OnSwitchRegisterListener {
        void onSwitchRegister();
    }

    private OnSwitchRegisterListener mOnSwitchRegisterListener = null;

    public void setOnSwitchRegisterListener(OnSwitchRegisterListener listener) {
        mOnSwitchRegisterListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.frag_login, container, false);
        //初始化界面
        initView(content);
        controler = Controler.getInstance();
        return content;
    }

    private void initView(View content) {
        accountInputLayout = (TextInputLayout) content.findViewById(R.id.login_account_input_layout);
        passwordInputLayout = (TextInputLayout) content.findViewById(R.id.login_password_input_layout);
        swichToregister = (Button) content.findViewById(R.id.bt_switch_register);
        login = (Button) content.findViewById(R.id.bt_login_sure);
        login.setOnClickListener(this);
        swichToregister.setOnClickListener(this);
        accountInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = accountInputLayout.getEditText().getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    accountInputLayout.getEditText().setText(str);
                    //设置新的光标所在位置
                    accountInputLayout.getEditText().setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = passwordInputLayout.getEditText().getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    passwordInputLayout.getEditText().setText(str);
                    //设置新的光标所在位置
                    passwordInputLayout.getEditText().setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login_sure:
                String name = accountInputLayout.getEditText().getText().toString();
                String password = passwordInputLayout.getEditText().getText().toString();
                controler.login(name, password, new Controler.OnUserStatusListener() {
                    @Override
                    public void onResult(int feedback, Supplier supplier) {
                        switch (feedback) {
                            case Customer.ALREADY_ONLINE:
                                Tool.toast("该账户以上线");

                                break;
                            case Customer.NOT_FOUND:
                                Tool.toast("该账户不存在");

                                break;
                            case Customer.PASSWORD_ERR:
                                Tool.toast("密码错误");

                                break;
                            case Customer.SUCCESS:
                                Tool.toast("成功登陆");
                                Tool.setUser(supplier);
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                break;
                            default:
                                Tool.toast("好像出了点小问题");
                                break;
                        }
                    }
                });
                break;
            case R.id.bt_switch_register:
                if (mOnSwitchRegisterListener != null) {
                    mOnSwitchRegisterListener.onSwitchRegister();
                }
                break;
        }
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
