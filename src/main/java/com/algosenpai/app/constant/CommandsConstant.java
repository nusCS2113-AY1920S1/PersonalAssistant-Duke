package com.algosenpai.app.constant;

import java.util.ArrayList;

public class CommandsConstant {

    public static final ArrayList<String> homeCommand = new ArrayList<>() {{
            add("/menu");
            add("/date");
            add("/sound");
            add("/start");
            add("/select");
            add("/report");
            add("/clear");
            add("/history");
            add("/help");
            add("/exit");
        }};
    public static final ArrayList<String> quizCommand = new ArrayList<>() {{
            add("/home");
            add("/end");
            add("/prev");
            add("/next");
            add("/save");
            add("/result");
            add("/help");
            add("/exit");
        }};
    public static final ArrayList<String> dateCommand = new ArrayList<>() {{
            add("/home");
            add("/exit");
        }};
    public static final ArrayList<String> endCommand = new ArrayList<>() {{
            add("/home");
            add("/date");
            add("/review");
            add("/exit");
        }};
    public static final ArrayList<String> girlsCommand = new ArrayList<>() {{
            add("/home");
            add("/select");
            add("/fun");
            add("/exit");
        }};
    public static final ArrayList<String> reviewCommand = new ArrayList<>() {{
            add("/home");
            add("/date");
            add("/exit");
        }};

}
