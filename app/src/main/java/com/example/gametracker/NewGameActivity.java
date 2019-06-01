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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class NewGameActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageView gameImage;
    private EditText gameName;
    private EditText hoursPlayed;
    private Spinner gameConsoleSelect;
    private String gameConsoleSelected;
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
        hoursPlayed = findViewById(R.id.editTextHoursPlayed);
        gameConsoleSelect = findViewById(R.id.spinnerGameSystem);
        gameConsoleSelect.setOnItemSelectedListener(this);
        arrayAdapter();
        Intent intent = getIntent();
        intent.getExtras();
        arrayPosition = intent.getIntExtra("Position", 0);
        Log.d("Position:", "" + arrayPosition);
        //När man redigerar redan inlagt spel
        if(arrayPosition >= 0){
            String gameImageString = intent.getStringExtra("Image");
            String gameNameString = intent.getStringExtra("Name");
            String gameConsoleString = intent.getStringExtra("Console");
            String hoursPlayedString = intent.getStringExtra("Hours");
            Bitmap currentImage = helpers.getBitmapFromString(gameImageString);
            gameImage.setImageBitmap(currentImage);
            gameName.setText(gameNameString);
            hoursPlayed.setText(hoursPlayedString);
            switch (gameConsoleString){
                case "Nintendo":
                    gameConsoleSelect.setSelection(0);
                    break;
                case "PC":
                    gameConsoleSelect.setSelection(1);
                    break;
                case "Playstation":
                    gameConsoleSelect.setSelection(2);
                    break;
                case "Xbox":
                    gameConsoleSelect.setSelection(3);
                    break;
                case "Other":
                    gameConsoleSelect.setSelection(4);
                    break;
            }
        }
    }

    //Adapter för spinner
    public void arrayAdapter(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.game_consoles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameConsoleSelect.setAdapter(adapter);
    }

    //Ta en bild
    public void addImage(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Spara spelet
    public void saveGame(View view){
        String bitMapString;
        arrayList = helpers.loadData(this);
        Intent intent = new Intent(this, ViewGameActivity.class);
        if(gameImage.getDrawable() == null){
            bitMapString = "";
        }else{
            Bitmap bitmap = ((BitmapDrawable)gameImage.getDrawable()).getBitmap();
            if(bitmap == null){
                bitMapString = "";
            }else{
                bitMapString = helpers.getStringFromBitmap(bitmap);
            }
        }
        String gameNameString = gameName.getText().toString();
        String hoursPlayedString = hoursPlayed.getText().toString();
        //Skapa nytt objekt och spara i lista
        if(arrayPosition == -1){
            arrayList.add(new Game(bitMapString, gameNameString, gameConsoleSelected, hoursPlayedString));
            intent.putExtra("Position", arrayList.size() - 1);
            //Uppdatera valt objekt
        }else{
            arrayList.get(arrayPosition).image = bitMapString;
            arrayList.get(arrayPosition).name = gameNameString;
            arrayList.get(arrayPosition).platform = gameConsoleSelected;
            arrayList.get(arrayPosition).hoursPlayed = hoursPlayedString;
        }
        helpers.saveData(arrayList, this);
        intent.putExtra("Image", bitMapString);
        intent.putExtra("Name", gameNameString);
        intent.putExtra("Console", gameConsoleSelected);
        intent.putExtra("Hours", hoursPlayedString);
        startActivity(intent);
    }

    //Ta emot bild
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            gameImage.setImageBitmap(imageBitmap);
        }
    }


    //Hantera vilken konsol man har valt i spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gameConsoleSelected = parent.getItemAtPosition(position).toString();
        Log.d("Konsol:", gameConsoleSelected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
