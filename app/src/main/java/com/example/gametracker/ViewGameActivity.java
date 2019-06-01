package com.example.gametracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewGameActivity extends AppCompatActivity {
    ImageView gameImage;
    TextView gameName;
    TextView gameConsole;
    TextView hoursPlayed;
    String imageBitmapString;
    Integer arrayPosition;
    Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        gameImage = findViewById(R.id.imageViewGameImage);
        gameName = findViewById(R.id.textViewGameName);
        gameConsole = findViewById(R.id.textViewGameConsole);
        hoursPlayed = findViewById(R.id.textViewHoursPlayed);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        intent.getExtras();
        imageBitmapString = intent.getStringExtra("Image");
        Bitmap stringImage = helpers.getBitmapFromString(imageBitmapString);
        gameImage.setImageBitmap(stringImage);
        String gameNames = intent.getStringExtra("Name");
        gameName.setText(gameNames);
        String gameConsoleString = intent.getStringExtra("Console");
        gameConsole.setText(gameConsoleString);
        String hoursPlayedString = intent.getStringExtra("Hours");
        hoursPlayed.setText(hoursPlayedString);
        arrayPosition = intent.getIntExtra("Position", -1);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> editGame());
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
}
