package com.example.gametracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewGameActivity extends AppCompatActivity {
    private String gameName;
    private TextView gameConsole;
    private TextView hoursPlayed;
    private String imageBitmapString;
    private String hoursPlayedString;
    private int arrayPosition;
    private ArrayList<Game> gameList;
    private Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        gameList = helpers.loadData(this);
        ImageView gameImage = findViewById(R.id.imageViewGameImage);
        gameConsole = findViewById(R.id.textViewGameConsole);
        hoursPlayed = findViewById(R.id.textViewHoursPlayed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        intent.getExtras();
        imageBitmapString = intent.getStringExtra("Image");
        Bitmap stringImage = helpers.getBitmapFromString(imageBitmapString);
        gameImage.setImageBitmap(stringImage);
        gameName = intent.getStringExtra("Name");
        this.setTitle(gameName);
        String gameConsoleString = intent.getStringExtra("Console");
        gameConsole.setText(gameConsoleString);
        hoursPlayedString = intent.getStringExtra("Hours");
        hoursPlayed.setText(getString(R.string.hours_played, hoursPlayedString));
        arrayPosition = intent.getIntExtra("Position", -1);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> editGame());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewgame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Gör ingenting
        if (item.getItemId() == R.id.action_delete_single) {
            showDeleteAlert();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editGame(){
        Intent intent = new Intent(this, NewGameActivity.class);
        intent.putExtra("Image", imageBitmapString);
        intent.putExtra("Name", gameName);
        intent.putExtra("Console", gameConsole.getText().toString());
        intent.putExtra("Hours", hoursPlayedString);
        intent.putExtra("Position", arrayPosition);
        startActivity(intent);
        finish();
    }

    private void showDeleteAlert(){
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(ViewGameActivity.this);
        //Varningsmeddelandet
        myAlertBuilder.setTitle(R.string.alert_are_you_sure);
        myAlertBuilder.setMessage(getString(R.string.alert_delete_single, gameName));
        //Knapparna för varningsmeddelandet
        //Ta bort listan
        myAlertBuilder.setPositiveButton(R.string.alert_yes, (dialog, which) -> {
            Intent intent = new Intent(this, MainActivity.class);
            gameList.remove(arrayPosition);
            Log.d("RADERAT", "" + arrayPosition);
            helpers.saveData(gameList, ViewGameActivity.this);
            startActivity(intent);
        });
        myAlertBuilder.setNegativeButton(R.string.alert_no, (dialog, which) -> {
            //Gör ingenting
        });
        //Skapa och visa varningsmeddelandet
        myAlertBuilder.show();
    }
}
