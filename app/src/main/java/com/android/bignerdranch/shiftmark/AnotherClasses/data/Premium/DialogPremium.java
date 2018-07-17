package com.android.bignerdranch.shiftmark.AnotherClasses.data.Premium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.android.bignerdranch.shiftmark.R;

/**
 * Created by X1opya on 08.07.2018.
 */

public class DialogPremium extends DialogFragment {

    private LayoutInflater inflater;
    private Premium p;

    public DialogPremium() {
        inflater = getLayoutInflater();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog,null);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setView(view).create();
    }

    public Premium getPrem(){
        return  p;
    }

    @Override
    public void show(android.app.FragmentManager manager, String tag) {
        super.show(manager, tag);
    }
}
