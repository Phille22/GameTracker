package com.example.gametracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewGameActivity extends AppCompatActivity {
    ImageView gameImage;
    TextView gameName;
    Helpers helpers;

    //TODO - Kunna redigera valt spel
    //TODO - Skicka valt spel till MainActivity för visning i recyclerview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        gameImage = findViewById(R.id.imageViewGameImage);
        gameName = findViewById(R.id.textViewGameName);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        intent.getExtras();
        String imageBitmapString = intent.getStringExtra("Image");
        Bitmap stringImage = helpers.getBitmapFromString(imageBitmapString);
        gameImage.setImageBitmap(stringImage);
        String gameNames = intent.getStringExtra("Name");
        gameName.setText(gameNames);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redigera spel
            }
        });
    }
}
