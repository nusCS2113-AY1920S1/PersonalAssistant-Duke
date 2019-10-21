package com.algosenpai.app.logic.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImagesConstant {

    public static Map<ImagesEnum, String> quizImages;
    public static Map<ImagesEnum, String> homeImages;
    public static Map<ImagesEnum, String> dateImages;
    public static Map<ImagesEnum, String> endImages;
    public static Map<ImagesEnum, String> girlsImages;
    public static Map<ImagesEnum, String> reviewImages;
    public static Map<ImagesEnum, String> characterImages;
    public static final ArrayList<String> characterImagesList = new ArrayList<>() {{
            add("miku.png");
            add("lolicon.png");
        }};

    static {
        quizImages = new HashMap<>();
        quizImages.put(ImagesEnum.MORNING_CLASSROOM, "morning-classroom.jpg");
        quizImages.put(ImagesEnum.AFTERNOON_CLASSROOM, "afternoon-classroom.jpg");
        quizImages.put(ImagesEnum.EVENING_CLASSROOM, "evening-classroom.jpg");
        quizImages.put(ImagesEnum.EMPTY_CLASSROOM, "empty-classroom.jpg");

        homeImages = new HashMap<>();
        homeImages.put(ImagesEnum.CUTE_ANIME, "cute-anime.png");

        dateImages = new HashMap<>();
        dateImages.put(ImagesEnum.PARK, "park.jpg");
        dateImages.put(ImagesEnum.BEDROOM, "bedroom.jpg");
        dateImages.put(ImagesEnum.SWIMMING_POOL, "swimming-pool.jpg");
        dateImages.put(ImagesEnum.TOUR, "tour.jpg");
        dateImages.put(ImagesEnum.TOWN, "town.jpg");

        endImages = new HashMap<>();
        endImages.put(ImagesEnum.EVENING, "evening.jpg");

        girlsImages = new HashMap<>();
        girlsImages.put(ImagesEnum.KISS, "kiss.png");

        reviewImages = new HashMap<>();
        reviewImages.put(ImagesEnum.SAO, "sao.jpeg");

        characterImages = new HashMap<>();
        characterImages.put(ImagesEnum.MIKU, "miku.png");
        characterImages.put(ImagesEnum.LOLICON, "lolicon.png");

    }
}
