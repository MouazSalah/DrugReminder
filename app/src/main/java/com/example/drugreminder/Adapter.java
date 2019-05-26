package com.example.drugreminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 4/25/2017.
 */

public class Adapter extends ArrayAdapter {
    Context c;
    ArrayList<String> userid, username;

    public Adapter(Context context, ArrayList<String> username, ArrayList<String> userid) {
        super(context, R.layout.list_item, R.id.text, username);
        this.c = context;
        this.username = username;
        this.userid = userid;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_item, parent, false);

        TextView text = (TextView) row.findViewById(R.id.text);
        TextView id = (TextView) row.findViewById(R.id.idtext);

        id.setText(userid.get(position));
        text.setText(username.get(position));

        return row;
    }
}
