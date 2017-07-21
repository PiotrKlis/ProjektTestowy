package com.example.pk.projekttestowy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PK on 21.07.2017.
 */

class ParametersAdapter extends ArrayAdapter<Parameters> {

    private Context mcontext;

    ParametersAdapter(Context context, Parameters[] params) {
        super(context, android.R.layout.simple_list_item_1, params);
        mcontext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_master, parent, false);

        TextView fullName = (TextView) row.findViewById(R.id.txtFullName);
        fullName.setText(getItem(position).full_name);

        return(row);
    }

}
