package com.android.bignerdranch.shiftmark.data.Premium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.bignerdranch.shiftmark.data.DataBase.DBEditor;
import com.android.bignerdranch.shiftmark.R;

import java.util.Calendar;

/**
 * Created by X1opya on 08.07.2018.
 */

public class DialogPremium extends DialogFragment {

    private LayoutInflater inflater;
    EditText many;
    EditText desc;
    TextView tv;
    Premium p;
    Boolean isReturn;


    public DialogPremium() {
        isReturn = false;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        many = view.findViewById(R.id.etMany);
        desc = view.findViewById(R.id.etDesc);
        tv = view.findViewById(R.id.non_many);
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(many.length()==0) tv.setText(R.string.non_many);
                else {
                    Bundle bundle = getArguments();
                    Calendar c = Calendar.getInstance();
                    c.set(bundle.getInt("y"),bundle.getInt("m"),bundle.getInt("d"));
                    int m = Integer.parseInt(many.getText().toString());
                    if(bundle.getBoolean("mod")==false)
                        m = -m;
                    p = new Premium(m, desc.getText().toString(), c);
                    DBEditor editor = new DBEditor(getContext());
                    editor.addPrem(p);
                }
            }
        });
        builder.setNegativeButton(R.string.unsave, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        return builder.setView(view).create();
    }

    @Override
    public void show(android.app.FragmentManager manager, String tag) {
        super.show(manager, tag);
    }
}
