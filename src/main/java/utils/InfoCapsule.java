package utils;

import duke.exception.DukeException;
import storage.task.TaskList;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import ui.UiCode;
import storage.wallet.Wallet;

public class InfoCapsule {
    private UiCode uiCode;
    private String outputStr;
    private Double outputDouble;
    private ObservableList<PieChart.Data> pieChartData;
    private TaskList taskList;
    private Wallet wallet;

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

    public void setUiCode(UiCode uiCode) {
        this.uiCode = uiCode;
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

    public void setOutputDouble(Double outputDouble) {
        this.outputDouble = outputDouble;
    }

    public Double getOutputDouble() {
        return outputDouble;
    }

    public void setPieChartData(ObservableList<PieChart.Data> pieChartData) {
        this.pieChartData = pieChartData;
    }

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
