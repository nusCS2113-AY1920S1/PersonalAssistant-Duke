package com.algosenpai.app.logic.constant;

import java.util.HashMap;
import java.util.Map;

public class SoundConstant {

    public static Map<SoundEnum, String> music;

    static {
        music = new HashMap<>();
        music.put(SoundEnum.ENDLESS_LOVE, "endless_love.wav");
        music.put(SoundEnum.LOST_CITY, "lost_city.wav");
    }
}
