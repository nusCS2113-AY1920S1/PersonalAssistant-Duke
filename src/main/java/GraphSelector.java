import controlpanel.DukeException;
import controlpanel.Parser;
import javafx.scene.layout.HBox;
import money.Account;

import java.io.IOException;
import java.text.ParseException;

public class GraphSelector implements DataTransfer {
    //@@author cctt1014
    public GraphSelector() {
    }

    public HBox getTheGraph(String input, Account account) throws IOException, ParseException, DukeException {
        if (input.startsWith("graph monthly report")) {
            if (input.equals("graph monthly report"))
                 return DataTransfer.getMonthlyData(account, Type.HISTOGRAM);
            input = input.replaceFirst("graph monthly report ", "");
            return DataTransfer.getMonthlyData(account, Type.valueOf(input.toUpperCase()));
        } else if (input.startsWith("graph expenditure trend")) {
            if (input.equals("graph expenditure trend"))
                return DataTransfer.getExpenditureTrend(account, Type.LINE_GRAPH);
            input = input.replaceFirst("graph expenditure trend ", "");
            return DataTransfer.getExpenditureTrend(account, Type.valueOf(input.toUpperCase()));
        } else if (input.startsWith("graph income trend")) {
            if (input.equals("graph income trend")) 
                return DataTransfer.getIncomeTrend(account, Type.LINE_GRAPH);
            input = input.replaceFirst("graph income trend ", "");
            return DataTransfer.getIncomeTrend(account, Type.valueOf(input.toUpperCase()));
        } else if (input.startsWith("graph finance status /until")) {
            String dateString = input.split(" /until ")[1];
            return  DataTransfer.getCurrFinance(account, Parser.shortcutTime(dateString));
        } else {
            throw new DukeException("Check the commands! The input is not valid.");
        }
    }
}