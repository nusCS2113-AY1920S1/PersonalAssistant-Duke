package com.algosenpai.app.logic.constant;

import java.util.HashMap;
import java.util.Map;

public class ViewConstant {

    public static Map<ViewEnum, String> quizView;
    public static Map<ViewEnum, String> homeView;
    public static Map<ViewEnum, String> dateView;
    public static Map<ViewEnum, String> endView;
    public static Map<ViewEnum, String> girlsView;
    public static Map<ViewEnum, String> reviewView;
    public static Map<ViewEnum, String> characterView;

    static {
        quizView = new HashMap<>();
        quizView.put(ViewEnum.QUIZ, "quiz.fxml");

        homeView = new HashMap<>();
        homeView.put(ViewEnum.HOME, "home.fxml");

        dateView = new HashMap<>();
        dateView.put(ViewEnum.DATE, "date.fxml");

        endView = new HashMap<>();
        endView.put(ViewEnum.END, "end.fxml");

        girlsView = new HashMap<>();
        girlsView.put(ViewEnum.GIRLS, "girls.fxml");

        reviewView = new HashMap<>();
        reviewView.put(ViewEnum.REVIEW, "review.fxml");

    }
}
