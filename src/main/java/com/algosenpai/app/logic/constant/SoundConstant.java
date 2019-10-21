package com.algosenpai.app.logic.constant;

import java.util.HashMap;
import java.util.Map;

public class SoundConstant {

    public static Map<SoundEnum, String> quizSound;
    public static Map<SoundEnum, String> homeSound;
    public static Map<SoundEnum, String> dateSound;
    public static Map<SoundEnum, String> endSound;
    public static Map<SoundEnum, String> girlsSound;
    public static Map<SoundEnum, String> reviewSound;

    static {
        quizSound = new HashMap<>();
        quizSound.put(SoundEnum.BURST_THE_GRAVITY, "burst-the-gravity.wav");
        quizSound.put(SoundEnum.GOTOBUN, "gotobun.wav");
        quizSound.put(SoundEnum.REZERO, "rezero.wav");
        quizSound.put(SoundEnum.ROMEO_AND_CINDERELLA, "romeo-and-cinderella.wav");
        quizSound.put(SoundEnum.SATURATION, "saturation.wav");
        quizSound.put(SoundEnum.SISTERS, "sisters.wav");

        homeSound = new HashMap<>();
        homeSound.put(SoundEnum.PROMISE, "promise.wav");

        dateSound = new HashMap<>();
        dateSound.put(SoundEnum.BURST_THE_GRAVITY, "burst-the-gravity.wav");
        dateSound.put(SoundEnum.GOTOBUN, "gotobun.wav");
        dateSound.put(SoundEnum.REZERO, "rezero.wav");
        dateSound.put(SoundEnum.ROMEO_AND_CINDERELLA, "romeo-and-cinderella.wav");
        dateSound.put(SoundEnum.SATURATION, "saturation.wav");
        dateSound.put(SoundEnum.SISTERS, "sisters.wav");

        endSound = new HashMap<>();
        endSound.put(SoundEnum.BURST_THE_GRAVITY, "burst-the-gravity.wav");
        endSound.put(SoundEnum.GOTOBUN, "gotobun.wav");
        endSound.put(SoundEnum.REZERO, "rezero.wav");
        endSound.put(SoundEnum.ROMEO_AND_CINDERELLA, "romeo-and-cinderella.wav");
        endSound.put(SoundEnum.SATURATION, "saturation.wav");
        endSound.put(SoundEnum.SISTERS, "sisters.wav");

        girlsSound = new HashMap<>();
        girlsSound.put(SoundEnum.ASAYAKE_NO_STARMINE, "asayake-no-starmine.wav");

        reviewSound = new HashMap<>();
        reviewSound.put(SoundEnum.BURST_THE_GRAVITY, "burst-the-gravity.wav");
        reviewSound.put(SoundEnum.GOTOBUN, "gotobun.wav");
        reviewSound.put(SoundEnum.REZERO, "rezero.wav");
        reviewSound.put(SoundEnum.ROMEO_AND_CINDERELLA, "romeo-and-cinderella.wav");
        reviewSound.put(SoundEnum.SATURATION, "saturation.wav");
        reviewSound.put(SoundEnum.SISTERS, "sisters.wav");

    }
}
