package com.android.bignerdranch.shiftmark.AnotherClasses.data.Premium;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bignerdranch.shiftmark.AnotherClasses.data.DataBase.DBEditor;
import com.android.bignerdranch.shiftmark.R;

import java.util.List;

/**
 * Created by X1opya on 03.07.2018.
 */

public class PremAdapter extends ArrayAdapter<Premium> {

    private LayoutInflater inflater;
    private int layout;
    private Context context;
    List<Premium> list;
    DBEditor editor;

    public PremAdapter(@NonNull Context context, int resource, @NonNull List<Premium> objects) {
        super(context, resource, objects);
        layout = resource;
        this.context = context;
        list = objects;
        editor = new DBEditor(context);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(layout,parent);
        TextView prem = view.findViewById(R.id.premium);
        TextView desc = view.findViewById(R.id.desc);
        ImageView img = view.findViewById(R.id.imageView);
        prem.setText(list.get(position).getMany());
        desc.setText(list.get(position).getDescription());
        if(list.get(position).getIntMany()<0) img.setImageResource(R.drawable.ic_remove_circle_black_24dp);
        else img.setImageResource(R.drawable.ic_add_circle_black_24dp);
        return  view;
    }

    @Override
    public void remove(@Nullable Premium object) {
        super.remove(object);
        editor.deletePrem(object.getIndex());
    }
}