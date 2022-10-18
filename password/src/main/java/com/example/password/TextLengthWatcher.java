package com.example.password;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.password.util.PasswordUtils;

/**
 * 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
 */
public class TextLengthWatcher implements TextWatcher {
    private Activity act;
    private EditText et;
    private int maxLength;
    public TextLengthWatcher(Activity act, EditText et, int maxLength) {
        this.act = act;
        this.et = et;
        this.maxLength = maxLength;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length() >= maxLength) {
            PasswordUtils.closeKeyboard(act, et);
        }
    }
}
