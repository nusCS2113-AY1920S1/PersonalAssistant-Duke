package executor.accessors;

import duke.exception.DukeException;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;
import storage.StorageManager;
import ui.UiCode;

public class AccessPieChartData extends Accessor {

    /**
     * Constructor for AccessPieChartData.
     */
    public AccessPieChartData() {
        super();
        this.accessType = AccessType.PIE_CHART_DATA;
    }

    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setUiCode(UiCode.UPDATE);
        Double walletBalance;
        Double walletExpenses;
        try {
            walletBalance = storageManager.getWalletBalance();
            walletExpenses = storageManager.getWalletExpenses();
        } catch (DukeException e) {
            e.printStackTrace();
            walletBalance = 0.01;
            walletExpenses = 0.01;
            this.infoCapsule.setUiCode(UiCode.ERROR);
            this.infoCapsule.setOutputStr(e.getMessage());
        }
        this.infoCapsule.setPieChartData(FXCollections.observableArrayList(
                new PieChart.Data("Expenses",
                        walletExpenses),
                new PieChart.Data("Balance",
                        walletBalance)));
    }
}
