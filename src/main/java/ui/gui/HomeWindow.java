package ui.gui;

import duke.exception.DukeException;
import storage.task.Task;
import interpreter.Interpreter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import storage.wallet.ReceiptTracker;
import ui.UiCode;
import storage.wallet.Wallet;
import executor.accessors.AccessType;
import utils.InfoCapsule;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeWindow extends AnchorPane {
    @FXML
    private TextField userInput;
    @FXML
    private DonutChart balanceChart;
    @FXML
    private Label balanceFigure;
    @FXML
    private StackedBarChart<String, Double> breakdownChart;
    @FXML
    private VBox taskContainerLeft;
    @FXML
    private VBox taskContainerRight;

    private Boolean exitRequest = false;
    private Interpreter interpreterLayer;
    private ArrayList<String> userInputHistory;
    private ObservableList<PieChart.Data> pieChartData;
    private XYChart.Series<String, Double> expenditureSeries;
    private XYChart.Series<String, Double> incomeSeries;
    private XYChart.Series<String, Double> backdrop;

    void initialize(ArrayList<String> userInputHistory, Interpreter interpreterLayer) throws DukeException {
        this.exitRequest = false;
        this.userInputHistory = userInputHistory;
        this.interpreterLayer = interpreterLayer;

        this.expenditureSeries = new XYChart.Series<>();
        this.expenditureSeries.setName("Expenditure");
        this.incomeSeries = new XYChart.Series<>();
        this.incomeSeries.setName("Income");
        this.backdrop = new XYChart.Series<>();
        this.backdrop.setName("Backdrop");

        this.displayTasks();
        this.displayBalanceChart();
        this.displayBreakdownChart();
    }

    private void extractPieChartData() throws DukeException {
        InfoCapsule infoCapsule = this.interpreterLayer.request(AccessType.PIE_CHART_DATA, null);
        if (infoCapsule.getUiCode() == UiCode.ERROR) {
            throw new DukeException(infoCapsule.getOutputStr());
        }

        this.pieChartData = infoCapsule.getPieChartData();
    }

    void updateBalanceChart() throws DukeException {
        InfoCapsule balanceCapsule = this.interpreterLayer.request(AccessType.BALANCE, null);
        InfoCapsule expensesCapsule = this.interpreterLayer.request(AccessType.EXPENSES, null);
        if (balanceCapsule.getUiCode() == UiCode.ERROR) {
            throw new DukeException(balanceCapsule.getOutputStr());
        }
        if (expensesCapsule.getUiCode() == UiCode.ERROR) {
            throw new DukeException(expensesCapsule.getOutputStr());
        }
        this.pieChartData.get(0).setPieValue(expensesCapsule.getOutputDouble());
        this.pieChartData.get(1).setPieValue(balanceCapsule.getOutputDouble());
        DecimalFormat decimalFormat = new DecimalFormat("$#0");
        this.balanceFigure.setText(decimalFormat.format(balanceCapsule.getOutputDouble()));
    }

    private void displayBalanceChart() throws DukeException {
        this.extractPieChartData();
        this.balanceChart.setData(this.pieChartData);
        this.balanceChart.setLegendVisible(false);
        this.balanceChart.setLabelsVisible(false);
        this.balanceChart.setStartAngle(90.0);
        String css = this.getClass().getResource("/css/PieChart.css").toExternalForm();
        this.balanceChart.getStylesheets().add(css);

        DecimalFormat decimalFormat = new DecimalFormat("$#0");
        Double walletBalance = getWalletBalance();
        this.balanceFigure.setText(decimalFormat.format(walletBalance));
    }

    private Double getWalletBalance() throws DukeException {
        InfoCapsule infoCapsule = this.interpreterLayer.request(AccessType.BALANCE, null);
        if (infoCapsule.getUiCode() == UiCode.UPDATE) {
            return infoCapsule.getOutputDouble();
        } else if (infoCapsule.getUiCode() == UiCode.ERROR) {
            throw new DukeException(infoCapsule.getOutputStr());
        } else {
            throw new DukeException("Unable to Access Wallet Balance");
        }
    }

    void displayTasks() throws DukeException {
        InfoCapsule infoCapsule = this.interpreterLayer.request(AccessType.TASKLIST, null);
        if (infoCapsule.getUiCode() == UiCode.ERROR) {
            throw new DukeException(infoCapsule.getOutputStr());
        }
        this.taskContainerRight.getChildren().clear();
        this.taskContainerLeft.getChildren().clear();
        for (Task task : infoCapsule.getTaskList()) {
            int leftListLength = this.taskContainerLeft.getChildren().size();
            int rightListLength = this.taskContainerRight.getChildren().size();
            if (leftListLength > rightListLength) {
                this.taskContainerRight.getChildren().add(TaskBox.getNewTaskBox(task));
            } else {
                this.taskContainerLeft.getChildren().add(TaskBox.getNewTaskBox(task));
            }
        }
    }

    private void displayBreakdownChart() {
        updateBreakdownData();

        this.breakdownChart.getData().add(this.expenditureSeries);
        this.breakdownChart.getData().add(this.incomeSeries);
        this.breakdownChart.getData().add(this.backdrop);
        this.breakdownChart.setScaleY(1.1);
        String css = this.getClass().getResource("/css/BreakdownChart.css").toExternalForm();
        this.breakdownChart.getStylesheets().add(css);
    }

    void updateBreakdownData() {
        InfoCapsule infoCapsule = this.interpreterLayer.request(AccessType.WALLET, null);
        Wallet wallet = infoCapsule.getWallet();
        HashMap<String, Double> newExpenditureData = new HashMap<>();
        HashMap<String, Double> newIncomeData = new HashMap<>();
        for (Map.Entry<String, ReceiptTracker> folder : wallet.getFolders().entrySet()) {
            if (isFolderValid(folder)) {
                newExpenditureData.put(folder.getKey(), folder.getValue().getTotalExpenses());
                newIncomeData.put(folder.getKey(), folder.getValue().getTotalIncome());
            }
        }
        Double backdropValue = this.getBackdropValue(wallet);
        HashMap<String, Double> newBackdropData = this.getBackdropData(backdropValue,
                this.expenditureSeries, this.incomeSeries);
        this.updateSeriesData(this.expenditureSeries, newExpenditureData);
        this.updateSeriesData(this.incomeSeries, newIncomeData);
        this.updateSeriesData(this.backdrop, newBackdropData);
    }

    private void updateSeriesData(XYChart.Series<String, Double> series, HashMap<String, Double> newData) {
        ArrayList<XYChart.Data<String, Double>> toDelete = new ArrayList<>();
        for (XYChart.Data<String, Double> oldDataPoint : series.getData()) {
            String key = oldDataPoint.getXValue();
            if (newData.containsKey(key)) {
                oldDataPoint.setYValue(newData.get(key));
                newData.remove(key);
            } else {
                toDelete.add(oldDataPoint);
            }
        }

        for (XYChart.Data<String, Double> item : toDelete) {
            series.getData().remove(item);
        }

        for (Map.Entry<String, Double> newDataPoint : newData.entrySet()) {
            series.getData().add(new XYChart.Data<String, Double>(
                    newDataPoint.getKey(),
                    newDataPoint.getValue()
            ));
        }
    }

    private boolean isFolderValid(Map.Entry<String, ReceiptTracker> folder) {
        if (folder.getKey().equals("Expenses")) {
            return false;
        } else if (folder.getKey().equals("Income")) {
            return false;
        } else if (folder.getValue().size() == 0) {
            return false;
        }
        return true;
    }

    private Double getBackdropValue(Wallet wallet) {
        double maxValue = 100.0;
        for (Map.Entry<String, ReceiptTracker> folder : wallet.getFolders().entrySet()) {
            if (!folder.getKey().equals("Income") && !folder.getKey().equals("Expenses")) {
                if (folder.getValue().getTotalExpenses() > maxValue) {
                    maxValue = folder.getValue().getTotalExpenses();
                } else if (folder.getValue().getTotalIncome() > maxValue) {
                    maxValue = folder.getValue().getTotalIncome();
                }
            }
        }
        return maxValue;
    }

    private HashMap<String, Double> getBackdropData(Double backdropValue,
                                                    XYChart.Series<String, Double> expenditureSeries,
                                                    XYChart.Series<String, Double> incomeSeries) {
        HashMap<String, Double> backdropData = new HashMap<>();
        for (XYChart.Data<String, Double> data : expenditureSeries.getData()) {
            backdropData.put(data.getXValue(), backdropValue - data.getYValue());
        }
        for (XYChart.Data<String, Double> data : incomeSeries.getData()) {
            if (backdropData.containsKey(data.getXValue())) {
                backdropData.replace(data.getXValue(), backdropData.get(data.getXValue()) - data.getYValue());
            } else {
                backdropData.put(data.getXValue(), backdropValue - data.getYValue());
            }
        }
        return backdropData;
    }
}
