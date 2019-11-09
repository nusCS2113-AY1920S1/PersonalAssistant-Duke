import javafx.scene.layout.HBox;
import money.Account;
import money.Expenditure;
import money.Income;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface DataTransfer {

    int NUMBER_OF_MONTHS = 3;

    enum Type {
        HISTOGRAM, LINE_GRAPH, PIE_CHART
    }

    //@@author cctt1014

    /**
     * This function gets the data of the current month's income and expenditure
     * from the account then passes the data to getHistogram method and return the
     * histogram it gets from getHistogram.
     * @param account The class contains all the user's data
     * @param type The chosen graph provided by the user
     * @return The graph chosen by the user for the monthly report
     * @throws IOException The IOE exception
     */
    static HBox getMonthlyData(Account account, Type type) throws IOException {
        ArrayList<String> dataX = new ArrayList<>();
        dataX.add("Income");
        dataX.add("Expenditure");
        ArrayList<Float> dataY = new ArrayList<>();
        dataY.add(account.getTotalIncome());
        dataY.add(account.getTotalExp());

        if (type.equals(Type.PIE_CHART)) {
            return CircleChart.getCircleChart("Monthly Data", dataX, dataY);
        } else if (type.equals(Type.LINE_GRAPH)) {
            return LineGraph.getLineGraph("Monthly Data", dataX, dataY);
        } else {
            return Histogram.getHistogram("Monthly Data", dataX, dataY);
        }
    }

    /**
     * This function helps to get the data about the user's expenditure category trend
     * from class Account, then transfer it to the specific getter to get the graph given
     * by the user. Finally, the getter will returns the graph.
     * @param account The class contains all the user's data
     * @param type The chosen graph provided by the user
     * @return The chosen graph with the expenditure category trend information
     * @throws IOException The IOE exception
     */
    static HBox getExpenditureTrend(Account account, Type type) throws IOException {
        ArrayList<Expenditure> expList = account.getExpListTotal();
        ArrayList<String> dataX = new ArrayList<>();
        ArrayList<Float> dataY = new ArrayList<>();
        for (Expenditure e : expList) {
            if (dataX.contains(e.getCategory())) {
                int index = dataX.indexOf(e.getCategory());
                dataY.set(index, dataY.get(index) + e.getPrice());
            } else {
                dataX.add(e.getCategory());
                dataY.add(e.getPrice());
            }
        }

        if (type.equals(Type.PIE_CHART)) {
            return CircleChart.getCircleChart("Overall Expenditure Trend", dataX, dataY);
        } else if (type.equals(Type.HISTOGRAM)) {
            return Histogram.getHistogram("Overall Expenditure Trend", dataX, dataY);
        } else {
            return LineGraph.getLineGraph("Overall Expenditure Trend", dataX, dataY);
        }
    }

    /**
     * This function helps to get the data about the user's income category trend
     * from class Account, then transfer it to the specific getter to get the graph given
     * by the user. Finally, the getter will returns the graph.
     * @param account The class contains all the user's data
     * @param type The chosen graph provided by the user
     * @return The chosen graph with the income category trend information
     * @throws IOException The IOE exception
     */
    static HBox getIncomeTrend(Account account, Type type) throws IOException {
        ArrayList<Income> incomeList = account.getIncomeListTotal();
        ArrayList<String> dataX = new ArrayList<>();
        ArrayList<Float> dataY = new ArrayList<>();
        for (Income e : incomeList) {
            if (dataX.contains(e.getDescription())) {
                int index = dataX.indexOf(e.getDescription());
                dataY.set(index, dataY.get(index) + e.getPrice());
            } else {
                dataX.add(e.getDescription());
                dataY.add(e.getPrice());
            }
        }
        if (type.equals(Type.PIE_CHART)) {
            return CircleChart.getCircleChart("Overall Income Trend", dataX, dataY);
        } else if (type.equals(Type.HISTOGRAM)) {
            return Histogram.getHistogram("Overall Income Trend", dataX, dataY);
        } else {
            return LineGraph.getLineGraph("Overall Income Trend", dataX, dataY);
        }
    }

    /**
     * This function helps to get the data about the user's 3-month income and expenditure
     * information until the given date from class Account, then transfer it to the specific getter
     * to get the 2-series histogram. Finally, the getter will returns the histogram.
     * @param account The class contains all the user's data
     * @param endDate The given end date for the financial status report
     * @return A histogram with the income and expenditure
     * @throws IOException The IOE exception
     */
    static Histogram getCurrFinance(Account account, LocalDate endDate) throws IOException {
        ArrayList<Income> incomeList = account.getIncomeListTotal();
        final ArrayList<Expenditure> expList = account.getExpListTotal();
        ArrayList<String> dataX = new ArrayList<>();
        ArrayList<Float> dataY1 = new ArrayList<>();
        ArrayList<Float> dataY2 = new ArrayList<>();

        LocalDate[] dateList = new LocalDate[NUMBER_OF_MONTHS + 1];
        for (int i = 0; i <= NUMBER_OF_MONTHS; i++) {
            dateList[i] = endDate.minusMonths(NUMBER_OF_MONTHS - i);
        }
        for (int i = NUMBER_OF_MONTHS - 1; i >= 0; i--) {
            dataX.add(String.valueOf(endDate.minusMonths(i).getMonthValue()));
            dataY1.add((float) 0);
            dataY2.add((float) 0);
        }

        for (Income e : incomeList) {
            for (int i = NUMBER_OF_MONTHS - 1; i >= 0; i--) {
                if (e.getPayday().isBefore(endDate) && e.getPayday().isAfter(dateList[i])) {
                    dataY1.set(i, dataY1.get(i) + e.getPrice());
                    break;
                }
            }
        }
        for (Expenditure e : expList) {
            for (int i = NUMBER_OF_MONTHS - 1; i >= 0; i--) {
                if (e.getDateBoughtDate().isBefore(endDate) && e.getDateBoughtDate().isAfter(dateList[i])) {
                    dataY2.set(i, dataY2.get(i) + e.getPrice());
                    break;
                }
            }
        }
        return Histogram.getTwoSeriesHistogram("Current Financial Status", dataX, dataY1, dataY2);
    }
}
