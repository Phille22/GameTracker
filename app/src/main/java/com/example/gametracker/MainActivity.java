package com.example.gametracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ArrayList<Game> gameList;
    private GameAdapter mAdapter;
    private Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameList = new ArrayList<>();
        gameList = helpers.loadData(this);
        recycleSetup();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), NewGameActivity.class);
            intent.putExtra("Position", -1);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameList = helpers.loadData(this);
        recycleSetup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_delete:
                showAlert();
                return true;
            case R.id.action_sort:
                View menuItemView = findViewById(R.id.action_sort);
                PopupMenu popup = new PopupMenu(this, menuItemView);
                popup.setOnMenuItemClickListener(this);
                popup.inflate(R.menu.menu_sort);
                popup.show();
                return true;
                default:
                    //Gör ingenting
        }
        return super.onOptionsItemSelected(item);
    }

    private void recycleSetup() {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new GameAdapter(this, gameList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //När man klickar på ett sorteringsalternativ
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_name:
                Collections.sort(gameList, (o1, o2) -> o1.name.compareTo(o2.name));
                mAdapter.notifyDataSetChanged();
                recycleSetup();
                helpers.saveData(gameList, MainActivity.this);
                return true;
            case R.id.sort_playtime:
                Collections.sort(gameList, (o1, o2) -> o2.hoursPlayed.compareTo(o1.hoursPlayed));
                mAdapter.notifyDataSetChanged();
                recycleSetup();
                helpers.saveData(gameList, MainActivity.this);
                return true;
            case R.id.sort_console:
                Collections.sort(gameList, (o1, o2) -> o1.platform.compareTo(o2.platform));
                mAdapter.notifyDataSetChanged();
                recycleSetup();
                helpers.saveData(gameList, MainActivity.this);
                default:
                    return false;
        }
    }

    //Visa varningsmeddelande
    private void showAlert(){
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
        //Varningsmeddelandet
        myAlertBuilder.setTitle(R.string.alert_are_you_sure);
        myAlertBuilder.setMessage(R.string.alert_delete_all);
        //Knapparna för varningsmeddelandet
        //Ta bort listan
        myAlertBuilder.setPositiveButton(R.string.alert_yes, (dialog, which) -> {
            gameList.clear();
            mAdapter.notifyDataSetChanged();
            helpers.saveData(gameList, MainActivity.this);
            recycleSetup();
        });
        myAlertBuilder.setNegativeButton(R.string.alert_no, (dialog, which) -> {
            //Gör ingenting
        });
        //Skapa och visa varningsmeddelandet
        myAlertBuilder.show();
    }
}
