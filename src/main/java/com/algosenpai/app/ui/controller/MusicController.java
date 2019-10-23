package com.algosenpai.app.ui.controller;

import com.algosenpai.app.logic.constant.SoundConstant;
import com.algosenpai.app.utility.ResourceRandomUtility;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class MusicController {

    private static MediaPlayer mediaPlayer;

    private static boolean isLoaded = false;

    private static boolean isMuted = false;

    public MusicController() throws URISyntaxException {
        playMusic();
    }

    static void playMusic() throws URISyntaxException {
        if (isLoaded) {
            mediaPlayer.stop();
        }
        randomMusic();
        isLoaded = true;
        mediaPlayer.setOnEndOfMedia(() -> {
            try {
                randomMusic();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    static void randomMusic() throws URISyntaxException {
        String musicName = ResourceRandomUtility.randomResources(SoundConstant.music);
        Media sound = new Media(MusicController.class.getResource("/sound/" + musicName).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    static void toggleVolume() {
        if (isMuted) {
            mediaPlayer.setMute(false);
            isMuted = false;
        } else {
            mediaPlayer.setMute(true);
            isMuted = true;
        }
    }
}
