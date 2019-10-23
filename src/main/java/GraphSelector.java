import controlpanel.Parser;
import javafx.scene.layout.HBox;
import money.Account;

import java.io.IOException;
import java.text.ParseException;

public class GraphSelector implements DataTransfer {
    public GraphSelector() {
    }

    public HBox getTheGraph(String input, Account account) throws IOException, ParseException {
        HBox graph = new HBox();
        switch (input) {
            case "graph monthly report":
                graph = DataTransfer.getMonthlyData(account);
                break;
            case "graph expenditure trend":
                graph =  DataTransfer.getExpenditureTrend(account);
                break;
            case "graph income trend":
                graph =  DataTransfer.getIncomeTrend(account);
                break;
            case "graph finance status /until":
                String dateString = input.split(" /until ")[1];
                graph =  DataTransfer.getCurrFinance(account, Parser.shortcutTime(dateString));
                break;
        }
        return graph;
    }
}