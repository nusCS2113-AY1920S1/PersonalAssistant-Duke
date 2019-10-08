package com.algosenpai.app.constant;

import java.util.HashMap;
import java.util.Map;

public class ImagesConstant {

    public static final int imageHeight = 300;
    public static final int imageWidth = 300;

    public static Map<ImagesEnum, String> quizImages;
    public static Map<ImagesEnum, String> startAppImages;

    static {
        quizImages = new HashMap<>();
        quizImages.put(ImagesEnum.BRIGHT_CLASSROOM, "bright-classroom.jpg");
        quizImages.put(ImagesEnum.CLASSROOM, "classroom.jpg");
        quizImages.put(ImagesEnum.EVENING_CLASSROOM, "evening-classroom.jpg");
        quizImages.put(ImagesEnum.EXAM_CLASSROOM, "exam-classroom.jpg");

        startAppImages = new HashMap<>();
        startAppImages.put(ImagesEnum.START_APP_1, "start-app-1.jpg");
        startAppImages.put(ImagesEnum.START_APP_2, "start-app-2.png");

    }
}
