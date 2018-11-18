package com.android.bignerdranch.shiftmark;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class DotWatcher implements TextWatcher {
    EditText et;

    public DotWatcher(EditText et) {
        this.et = et;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(i2>0 && charSequence.toString().toCharArray()[0] == '.'){
            et.setText("");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
