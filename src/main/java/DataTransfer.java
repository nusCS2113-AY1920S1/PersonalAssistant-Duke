import money.Account;
import money.Expenditure;
import money.Income;

import java.io.IOException;
import java.util.ArrayList;

public interface DataTransfer {

    /**
     * This function sends the data of histogram for the monthly report
     * @return histogram for the monthly report
     */
    static Histogram getMonthlyData(Account account) throws IOException {
        ArrayList<String> xData = new ArrayList<>();
        xData.add("Income");
        xData.add("Expenditure");
        ArrayList<Float> yData = new ArrayList<>();
        yData.add(account.getTotalIncome());
        yData.add(account.getTotalExp());
        return Histogram.getHistogram("The Month Report", xData, yData);
    }

    static LineGraph getExpenditureTrend(Account account) throws IOException {
        ArrayList<Expenditure> expList = account.getExpListTotal();
        ArrayList<String> xData = new ArrayList<>();
        ArrayList<Float> yData = new ArrayList<>();
        for (Expenditure e : expList) {
            if (xData.contains(e.getCategory())) {
                int index = xData.indexOf(e.getCategory());
                yData.set(index, yData.get(index) + e.getPrice());
            } else {
                xData.add(e.getCategory());
                yData.add(e.getPrice());
            }
        }
        return LineGraph.getLineGraph("Overall Expenditure Trend", xData, yData);
    }

    static LineGraph getIncomeTrend(Account account) throws IOException {
        ArrayList<Income> incomeList = account.getIncomeListTotal();
        ArrayList<String> xData = new ArrayList<>();
        ArrayList<Float> yData = new ArrayList<>();
        for (Income e : incomeList) {
            if (xData.contains(e.getDescription())) {
                int index = xData.indexOf(e.getDescription());
                yData.set(index, yData.get(index) + e.getPrice());
            } else {
                xData.add(e.getDescription());
                yData.add(e.getPrice());
            }
        }
        return LineGraph.getLineGraph("Overall Income Trend", xData, yData);
    }

}
