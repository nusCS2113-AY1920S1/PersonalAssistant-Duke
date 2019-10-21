package com.algosenpai.app.ui.controller;

import com.algosenpai.app.logic.constant.ResourcePathConstant;
import com.algosenpai.app.logic.constant.SoundConstant;
import com.algosenpai.app.logic.constant.SoundEnum;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

class MusicController {

    private static MediaPlayer mediaPlayer;

    private static boolean isLoaded = false;

    private static boolean isMuted = false;

    MusicController() {
        playMusic(SoundConstant.homeSound.get(SoundEnum.PROMISE));
    }

    /**
     * Play and change music.
     * @param musicName name of the music.
     */
    static void playMusic(String musicName) {
        if (isLoaded) {
            mediaPlayer.stop();
        }
        String musicFile = ResourcePathConstant.soundFilePath + musicName;
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        isLoaded = true;
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
