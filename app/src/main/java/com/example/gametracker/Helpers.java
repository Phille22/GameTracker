package com.example.gametracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Helpers extends AppCompatActivity {

    public static Bitmap getBitmapFromString(String stringPicture){
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitMapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY, byteArrayBitMapStream);
        byte[] b = byteArrayBitMapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap scaleDownBitmap(Bitmap bitmap, int newHeight, Context context) {
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;
        int h = (int) (newHeight * densityMultiplier);
        int w = (int) (h * bitmap.getWidth() / (double) bitmap.getHeight());
        bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
        return bitmap;
    }

    public static void saveData(ArrayList arrayList, Context context) {
        String filename = "GameData.json";
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            Writer writer = new OutputStreamWriter(outputStream);
            Gson gson = new Gson();
            gson.toJson(arrayList, writer);
            writer.close();
            Log.d("Data sparat:", "" + arrayList);
        } catch (Exception e) {
            Log.e("Can´t save data", "", e);
        }
    }

    public static ArrayList<Game> loadData(Context context) {
        String filename = "GameData.json";
        ArrayList arrayList = new ArrayList<>();
            try {
                FileInputStream inputStream;
                inputStream = context.openFileInput(filename);
                Reader reader = new BufferedReader(new InputStreamReader(inputStream));
                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<Game>>() {
                }.getType();
                arrayList = gson.fromJson(reader, collectionType);
                reader.close();
                Log.d("Data laddat:", "" + arrayList);
            } catch (Exception e) {
                Log.e("Can´t load data", "", e);
            }
        return arrayList;
    }
}
