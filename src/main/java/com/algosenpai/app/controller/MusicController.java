package com.algosenpai.app.controller;

import com.algosenpai.app.constant.FilePath;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

class MusicController {

    private static MediaPlayer mediaPlayer;

    private static boolean isLoaded = false;

    MusicController() {
        playMusic("asayake-no-starmine.wav");
    }

    /**
     * Play and change music.
     * @param musicName name of the music.
     */
    static void playMusic(String musicName) {
        if (isLoaded) {
            mediaPlayer.stop();
        }
        String musicFile = FilePath.soundFilePath + musicName;
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        isLoaded = true;
    }

}
