package controlpanel;

import controlpanel.Parser;
import javafx.scene.layout.HBox;
import money.Account;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

public class GraphSelector implements DataTransfer {
    //@@author cctt1014
    public GraphSelector() {
    }

    /**
     * This method handles the graph related command to get the graph
     * with required data in the specific patter.
     * @param input The input string
     * @param account The class contains all the user's data
     * @return The desired graph with the data
     * @throws IOException The IOE exception
     * @throws ParseException The parse exception
     */
    public HBox getTheGraph(String input, Account account) throws IOException, ParseException, DukeException {
        if (input.startsWith("graph monthly report")) {
            if (input.equals("graph monthly report")) {
                return DataTransfer.getMonthlyData(account, "HISTOGRAM");
            }
            input = input.replaceFirst("graph monthly report ", "");
            return DataTransfer.getMonthlyData(account, input.toUpperCase());
        } else if (input.startsWith("graph expenditure trend")) {
            if (input.equals("graph expenditure trend")) {
                return DataTransfer.getExpenditureTrend(account, "LINE_GRAPH");
            }
            input = input.replaceFirst("graph expenditure trend ", "");
            return DataTransfer.getExpenditureTrend(account, input.toUpperCase());
        } else if (input.startsWith("graph income trend")) {
            if (input.equals("graph income trend")) {
                return DataTransfer.getIncomeTrend(account, "LINE_GRAPH");
            }
            input = input.replaceFirst("graph income trend ", "");
            return DataTransfer.getIncomeTrend(account, input.toUpperCase());
        } else if (input.startsWith("graph finance status /until")) {
            try {
                String dateString = input.split(" /until ")[1];
                return  DataTransfer.getCurrFinance(account, Parser.shortcutTime(dateString));
            } catch (DateTimeParseException e) {
                throw new DukeException("Invalid date! Please enter date in the format: d/m/yyyy\n");
            }

        } else {
            return null;
        }
    }
}