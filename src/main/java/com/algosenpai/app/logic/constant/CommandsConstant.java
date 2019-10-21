package com.algosenpai.app.logic.constant;

import java.util.ArrayList;

public class CommandsConstant {

    public static final ArrayList<String> homeCommand = new ArrayList<>() {{
            add("/play");
            add("/date");
            add("/sound");
            add("/exit");
        }};
    public static final ArrayList<String> quizCommand = new ArrayList<>() {{
            add("/home");
            add("/end");
            add("/prev");
            add("/next");
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
