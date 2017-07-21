package com.example.pk.projekttestowy;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Master");

        fetchData();

        // Pull-to-refresh
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        fetchData();
                        refreshLayout.setRefreshing(false);
                    }
                }

        );
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    // Managing data which came back from data download thread
    public void onEventMainThread(ParametersLoadedEvent event) {

        ListView listView = (ListView) findViewById(R.id.master_list);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);

        listView.setAdapter(new ParametersAdapter(getApplicationContext(), event.getParameters()));

        progressBar.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    // Function which downloads data from API
    private boolean fetchData() {

        final ListView listView = (ListView) findViewById(R.id.master_list);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);

        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        // Start a thread to load data
        new LoadThread().start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Parameters param = (Parameters) listView.getItemAtPosition(position);

                Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
                myIntent.putExtra("id", param.id);
                myIntent.putExtra("name", param.name);
                myIntent.putExtra("fullName", param.full_name);
                myIntent.putExtra("htmlUrl", param.html_url);
                startActivity(myIntent);

            }
        });

        return true;
    }

    //Thread which downloads data in a background
    private class LoadThread extends Thread {
        static final String POSTS_URL = "https://api.github.com/users/google/repos";
        @Override
        public void run() {
            try {
                HttpsURLConnection c =
                        (HttpsURLConnection ) new URL(POSTS_URL).openConnection();
                try {
                    InputStream in = c.getInputStream();
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(in));
                    Parameters[] parameters =
                            new Gson().fromJson(reader, Parameters[].class);
                    reader.close();

                    EventBus.getDefault().post(new ParametersLoadedEvent(parameters));

                } catch (IOException e) {
                    Log.e(getClass().getSimpleName(), "JSON parse error", e);
                } finally {
                    c.disconnect();
                }
            }
            catch (Exception e) {
                Log.e(getClass().getSimpleName(), "JSON parse error", e);
            }
        }
    }
}