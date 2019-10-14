import money.Account;
import money.Expenditure;

import java.io.IOException;
import java.util.ArrayList;

public interface DataTransfer {

    /**
     * This function sends the data of histogram for the monthly report
     * @return histogram for the monthly report
     */
    static Histogram getMonthlyData(Account account) throws IOException {
        String[] xData = new String[]{"Income", "Expenditure"};
        float[] yData = new float[]{account.getTotalIncome(), account.getTotalExp()};
        return Histogram.getHistogram("The Month Report", xData, yData);
    }

//    static LineGraph getCategoryTrend(Account account) {
//        ArrayList<Expenditure> expList = account.getExpListTotal();
//
//    }



}
