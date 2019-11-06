package utils;

import duke.exception.DukeException;
import ui.UiCode;

public class InfoCapsule {
    private UiCode uiCode;
    private String outputStr;

    public void throwError() throws DukeException {
        throw new DukeException(outputStr);
    }

    // Setters & Getters
    public void setCodeToast() {
        this.uiCode = UiCode.TOAST;
    }

    public void setCodeCli() {
        this.uiCode = UiCode.CLI;
    }

    public void setCodeUpdate() {
        this.uiCode = UiCode.UPDATE;
    }

    public void setCodeError() {
        this.uiCode = UiCode.ERROR;
    }

    public void setCodeExit() {
        this.uiCode = UiCode.EXIT;
    }

    public UiCode getUiCode() {
        return this.uiCode;
    }

    public void setOutputStr(String outputStr) {
        this.outputStr = outputStr;
    }

    public String getOutputStr() {
        return outputStr;
    }
}
