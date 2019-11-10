package moomoo.stubs;

import moomoo.feature.Ui;

public class UiStub extends Ui {
    private static String response;

    public static String returnResponse() {
        return response;
    }

    public static void setOutput(String output) {
        response = output;
    }

}
