package com.algosenpai.app.constant;

import java.util.HashMap;
import java.util.Map;

public class ImagesConstant {

    public static final int imageHeight = 300;
    public static final int imageWidth = 300;

    public static Map<ImagesEnum, String> quizImages;

    static {
        quizImages = new HashMap<>();
        quizImages.put(ImagesEnum.BRIGHT_CLASSROOM, "bright-classroom.jpg");
        quizImages.put(ImagesEnum.CLASSROOM, "classroom.jpg");
        quizImages.put(ImagesEnum.EVENING_CLASSROOM, "evening-classroom.jpg");
        quizImages.put(ImagesEnum.EXAM_CLASSROOM, "exam-classroom.jpg");
    }
}
