package com.algosenpai.app.ui.controller;

import com.algosenpai.app.logic.constant.SoundConstant;
import com.algosenpai.app.utility.ResourceRandomUtility;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class MusicController {

    private static MediaPlayer mediaPlayer;

    private static boolean isLoaded = false;

    private MusicController() {
    }

    public static MusicController getMusicController() {
        return new MusicController();
    }

    /**
     * Starts music player.
     * @throws URISyntaxException error in playing current music.
     */
    public static void playMusic() throws URISyntaxException {
        if (isLoaded) {
            mediaPlayer.stop();
        }
        randomMusic();
        isLoaded = true;
        mediaPlayer.setOnEndOfMedia(() -> {
            try {
                randomMusic();
            } catch (URISyntaxException e) {
                Platform.exit();
            }
        });
    }

    static void randomMusic() throws URISyntaxException {
        String musicName = ResourceRandomUtility.randomResources(SoundConstant.music);
        Media sound = new Media(MusicController.class.getResource("/sound/" + musicName).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static void setVolume(double soundLevel) {
        mediaPlayer.setVolume(soundLevel);
    }
}
