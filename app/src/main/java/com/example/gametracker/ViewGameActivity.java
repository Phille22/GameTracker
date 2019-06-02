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
    ImageView gameImage;
    TextView gameName;
    TextView gameConsole;
    TextView hoursPlayed;
    String imageBitmapString;
    int arrayPosition;
    public ArrayList<Game> gameList;
    Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        gameList = helpers.loadData(this);
        gameImage = findViewById(R.id.imageViewGameImage);
        gameName = findViewById(R.id.textViewGameName);
        gameConsole = findViewById(R.id.textViewGameConsole);
        hoursPlayed = findViewById(R.id.textViewHoursPlayed);;
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        intent.getExtras();
        imageBitmapString = intent.getStringExtra("Image");
        Bitmap stringImage = helpers.getBitmapFromString(imageBitmapString);
        gameImage.setImageBitmap(stringImage);
        String gameNames = intent.getStringExtra("Name");
        this.setTitle(gameNames);
        gameName.setText(gameNames);
        String gameConsoleString = intent.getStringExtra("Console");
        gameConsole.setText(gameConsoleString);
        String hoursPlayedString = intent.getStringExtra("Hours");
        hoursPlayed.setText(hoursPlayedString);
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
        switch (item.getItemId()) {
            case R.id.action_delete_single:
                showDeleteAlert();
                return true;
                default:
                    //Gör ingenting
        }
        return super.onOptionsItemSelected(item);
    }

    private void editGame(){
        Intent intent = new Intent(this, NewGameActivity.class);
        intent.putExtra("Image", imageBitmapString);
        intent.putExtra("Name", gameName.getText().toString());
        intent.putExtra("Console", gameConsole.getText().toString());
        intent.putExtra("Hours", hoursPlayed.getText().toString());
        intent.putExtra("Position", arrayPosition);
        startActivity(intent);
        finish();
    }

    public void showDeleteAlert(){
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(ViewGameActivity.this);
        //Varningsmeddelandet
        myAlertBuilder.setTitle("Är du säker?");
        myAlertBuilder.setMessage("Vill du radera " + gameName.getText().toString() + "?");
        //Knapparna för varningsmeddelandet
        //Ta bort listan
        myAlertBuilder.setPositiveButton("JA", (dialog, which) -> {
            Intent intent = new Intent(this, MainActivity.class);
            gameList.remove(arrayPosition);
            Log.d("RADERAT", "" + arrayPosition);
            helpers.saveData(gameList, ViewGameActivity.this);
            startActivity(intent);
        });
        myAlertBuilder.setNegativeButton("NEJ", (dialog, which) -> {
            //Gör ingenting
        });
        //Skapa och visa varningsmeddelandet
        myAlertBuilder.show();
    }
}
