package com.android.bignerdranch.shiftmark;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.processphoenix.ProcessPhoenix;

public class DestroyDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Закрыть приложение").setMessage("Мы нашли данные вашего аккаунта. Чтобы они появились нужно перезапустить приложение")
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.println(Log.ASSERT,"TAG DIAL", "Клик диалогового окна");
                        ProcessPhoenix.triggerRebirth(getActivity().getApplication(),getActivity().getIntent());
                    }
                }).create();
    }




}
