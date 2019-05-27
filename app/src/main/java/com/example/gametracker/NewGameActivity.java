package com.example.gametracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class NewGameActivity extends AppCompatActivity {
    private ImageView gameImage;
    private EditText gameName;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public ArrayList<Game> arrayList;
    public Integer arrayPosition;
    Helpers helpers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        gameImage = findViewById(R.id.imageViewNewImage);
        gameName = findViewById(R.id.editTextGameName);
        Intent intent = getIntent();
        intent.getExtras();
        arrayPosition = intent.getIntExtra("Position", 0);
        Log.d("Position:", "" + arrayPosition);
        if(arrayPosition >= 0){
            String gameImageString = intent.getStringExtra("Image");
            String gameNameString = intent.getStringExtra("Name");
            Bitmap currentImage = helpers.getBitmapFromString(gameImageString);
            gameImage.setImageBitmap(currentImage);
            gameName.setText(gameNameString);
        }
    }

    public void addImage(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void saveGame(View view){
        arrayList = helpers.loadData(this);
        Intent intent = new Intent(this, ViewGameActivity.class);
        Bitmap bitmap = ((BitmapDrawable)gameImage.getDrawable()).getBitmap();
        String bitMapString = helpers.getStringFromBitmap(bitmap);
        String gameNameString = gameName.getText().toString();
        if(arrayPosition == -1){
            arrayList.add(new Game(bitMapString, gameNameString));
        }else{
            arrayList.get(arrayPosition).image = bitMapString;
            arrayList.get(arrayPosition).name = gameNameString;
        }
        helpers.saveData(arrayList, this);
        intent.putExtra("Image", bitMapString);
        intent.putExtra("Name", gameNameString);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            gameImage.setImageBitmap(imageBitmap);
        }
    }


}
