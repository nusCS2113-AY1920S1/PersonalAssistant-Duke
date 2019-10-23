import money.Account;
import money.Expenditure;
import money.Income;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface DataTransfer {

    int NUMBER_OF_MONTHS = 3;

    /**
     * This function gets the data of the current month's income and expenditure
     * from the account then passes the data to getHistogram method and return the
     * histogram it gets from getHistogram.
     * @return histogram for the monthly report
     */
    //@@ cctt1014
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

    static Histogram getCurrFinance(Account account, LocalDate endDate) throws IOException {
        ArrayList<Income> incomeList = account.getIncomeListTotal();
        ArrayList<Expenditure> expList = account.getExpListTotal();
        ArrayList<String> xData = new ArrayList<>();
        ArrayList<Float> yData1 = new ArrayList<>();
        ArrayList<Float> yData2 = new ArrayList<>();

        LocalDate[] dateList = new LocalDate[NUMBER_OF_MONTHS+1];
        for (int i = 0; i <= NUMBER_OF_MONTHS; i++) {
            dateList[i] = endDate.minusMonths(NUMBER_OF_MONTHS-i);
        }
        for (int i = NUMBER_OF_MONTHS-1; i >= 0; i--) {
            xData.add(String.valueOf(endDate.minusMonths(i).getMonthValue()));
            yData1.add((float) 0);
            yData2.add((float) 0);
        }

        for (Income e : incomeList) {
            for (int i = NUMBER_OF_MONTHS-1; i >= 0; i--) {
                if (e.getPayday().isBefore(endDate) && e.getPayday().isAfter(dateList[i])) {
                    yData1.set(i, yData1.get(i)+e.getPrice());
                    break;
                }
            }
        }
        for (Expenditure e : expList) {
            for (int i = NUMBER_OF_MONTHS-1; i >= 0; i--) {
                if (e.getDateBoughtDate().isBefore(endDate) && e.getDateBoughtDate().isAfter(dateList[i])) {
                    yData2.set(i, yData2.get(i)+e.getPrice());
                    break;
                }
            }
        }
        return Histogram.getTwoSeriesHistogram("Current Financial Status", xData, yData1, yData2);
    }
}
