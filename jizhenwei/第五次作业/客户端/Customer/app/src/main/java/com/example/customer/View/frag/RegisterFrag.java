package com.example.customer.View.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.customer.Control.Controler;
import com.example.customer.Model.Customer;
import com.example.customer.R;
import com.example.customer.Util.Tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class RegisterFrag extends Fragment implements View.OnClickListener {
    EditText account, password;
    Button register;
    Controler controler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.frag_register, container, false);
        initView(content);
        controler = Controler.getInstance();
        return content;
    }

    private void initView(View content) {
        account = (EditText) content.findViewById(R.id.et_account_register);
        password = (EditText) content.findViewById(R.id.et_register_password);
        register = (Button) content.findViewById(R.id.bt_register_sure);
        register.setOnClickListener(this);
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = account.getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    account.setText(str);
                    //设置新的光标所在位置
                    account.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = password.getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    password.setText(str);
                    //设置新的光标所在位置
                    password.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onClick(register);
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register_sure:
                String name = account.getText().toString();
                String pass = password.getText().toString();
                controler.register(name, pass, new Controler.OnUserStatusListener() {
                    @Override
                    public void onResult(int feedback, Customer customer) {
                        switch (feedback) {
                            case Customer.ALREADY_EXIST:
                                Tool.toast("该账户以注册");
                                break;
                            case Customer.SUCCESS:
                                Tool.toast("成功注册");
                                getFragmentManager().popBackStack();
                                break;
                            default:
                                Tool.toast("好像出了点小问题");
                                break;
                        }
                    }
                });
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
