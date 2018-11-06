package com.android.bignerdranch.shiftmark.data.Premium;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.bignerdranch.shiftmark.data.DataBase.DBEditor;
import com.android.bignerdranch.shiftmark.R;

import java.util.Collection;
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
        View view = inflater.inflate(layout,parent,false);
        TextView prem = view.findViewById(R.id.premium);
        TextView desc = view.findViewById(R.id.desc);
        prem.setText(list.get(position).getMany());
        desc.setText(list.get(position).getDescription());
        return  view;
    }

    public List<Premium> getPrems(){
        return list;
    }


    @Override
    public void addAll(@NonNull Collection<? extends Premium> collection) {
        list.clear();
        super.addAll(collection);
    }

    @Override
    public void remove(@Nullable Premium object) {
        super.remove(object);
        editor.deletePrem(object.getIndex());
    }
}
