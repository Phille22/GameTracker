package com.example.gametracker;

class Game {
    String image;
    String name;
    String platform;
    String hoursPlayed;

    Game(String image, String name, String platform, String hoursPlayed){
        this.image = image;
        this.name = name;
        this.platform = platform;
        this.hoursPlayed = hoursPlayed;
    }
}
