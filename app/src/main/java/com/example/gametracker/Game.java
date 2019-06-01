package com.example.gametracker;

public class Game {
    String image;
    String name;
    String platform;
    String hoursPlayed;

    public Game(String image, String name, String platform, String hoursPlayed){
        this.image = image;
        this.name = name;
        this.platform = platform;
        this.hoursPlayed = hoursPlayed;
    }

    public String getName(){
        return this.name;
    }
}
