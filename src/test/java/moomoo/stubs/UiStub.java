package moomoo.stubs;

import moomoo.task.Ui;

public class UiStub extends Ui {
    private String response;

    @Override
    public String returnResponse() {
        return this.response;
    }

    @Override
    public void setOutput(String output) {
        this.response = output;
    }
}
