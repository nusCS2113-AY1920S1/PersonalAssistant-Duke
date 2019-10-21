package com.algosenpai.app.logic.constant;

import java.util.HashMap;
import java.util.Map;

public class SoundConstant {

    public static Map<SoundEnum, String> music;

    static {
        music = new HashMap<>();
        music.put(SoundEnum.BURST_THE_GRAVITY, "burst-the-gravity.wav");
        music.put(SoundEnum.GOTOBUN, "gotobun.wav");
        music.put(SoundEnum.REZERO, "rezero.wav");
        music.put(SoundEnum.ROMEO_AND_CINDERELLA, "romeo-and-cinderella.wav");
        music.put(SoundEnum.SATURATION, "saturation.wav");
        music.put(SoundEnum.SISTERS, "sisters.wav");
        music.put(SoundEnum.PROMISE, "promise.wav");
        music.put(SoundEnum.ASAYAKE_NO_STARMINE, "asayake-no-starmine.wav");
    }
}
