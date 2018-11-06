package com.android.bignerdranch.shiftmark;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.bignerdranch.shiftmark.data.DayData.DaySettings;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by X1opya on 30.04.2018.
 */


public class DataEditor{

    private static final String FILENAME = "pathern.txt";

public static void savePathern(Context context, DaySettings pathern){
    Gson json = new Gson();
    FileOutputStream fos = null;
    try {
        fos = context.openFileOutput(FILENAME,MODE_PRIVATE);
        fos.write(json.toJson(pathern).getBytes());
        fos.close();
        Toast.makeText(context,R.string.accept_pathern,Toast.LENGTH_SHORT).show();
        Log.println(Log.ASSERT,"Json Messeg:::: Output",json.toJson(pathern));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

 public static DaySettings intentFromPathern(Context context){
    Gson json = new Gson();
     FileInputStream stream;
     InputStreamReader reader;
     try {
         stream = context.openFileInput(FILENAME);
         reader = new InputStreamReader(stream);
         DaySettings pathern = json.fromJson(reader,DaySettings.class);
         Log.println(Log.ASSERT,"Json Messeg:::: Input","прочитал");
         return pathern;
     } catch (FileNotFoundException e) {
         return new DaySettings();
     } catch (IOException e) {
         return new DaySettings();
     }
 }

}
