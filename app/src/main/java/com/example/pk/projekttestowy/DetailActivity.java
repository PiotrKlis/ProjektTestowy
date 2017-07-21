package com.example.pk.projekttestowy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by PK on 21.07.2017.
 */

public class DetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.row_detail);

        TextView txtId = (TextView) findViewById(R.id.txtId);
        TextView txtName = (TextView) findViewById(R.id.txtName);
        TextView txtFullName = (TextView) findViewById(R.id.txtFullName);
        TextView txtHtmlUrl = (TextView) findViewById(R.id.txtHtmlUrl);

        txtId.setText(String.valueOf(getIntent().getExtras().getInt("id")));
        txtName.setText(getIntent().getExtras().getString("name"));
        txtFullName.setText(getIntent().getExtras().getString("fullName"));
        txtHtmlUrl.setText(getIntent().getExtras().getString("htmlUrl"));

    }
}
