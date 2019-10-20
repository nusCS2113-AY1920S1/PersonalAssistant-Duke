package moomoo.stubs;

import moomoo.task.Ui;

public class UiStub extends Ui {
    private String response;

    public UiStub() {

    }

    @Override
    public String printResponse() {
        return this.response;
    }

    @Override
    public void setOutput(String output) {
        this.response = output;
    }
}
