package com.algosenpai.app.ui.controller;

import com.algosenpai.app.logic.constant.SoundConstant;
import com.algosenpai.app.utility.ResourceRandomUtility;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class MusicController {

    /**
     * Media player to start playing music.
     */
    private static MediaPlayer mediaPlayer;

    /**
     * Playing status of music player.
     */
    private static boolean isLoaded = false;

    /**
     * Sound level is of range 0 to 100.
     */
    private static int MAX_VOLUME = 100;

    private MusicController() {
    }

    /**
     * Returns only one instance of music controller.
     * @return instance of music controller.
     */
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

    /**
     * Selects the next music randomly.
     * @throws URISyntaxException music not found.
     */
    static void randomMusic() throws URISyntaxException {
        String musicName = ResourceRandomUtility.randomResources(SoundConstant.music);
        Media sound = new Media(MusicController.class.getResource("/sound/" + musicName).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    /**
     * Adjust the sound volume.
     * @param soundLevel volume range 0 to 100
     */
    public static void setVolume(double soundLevel) {
        final float volume = (float) (1 - (Math.log(MAX_VOLUME - soundLevel) / Math.log(MAX_VOLUME)));
        mediaPlayer.setVolume(volume);
    }


}
