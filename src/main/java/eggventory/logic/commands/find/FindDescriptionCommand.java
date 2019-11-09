package eggventory.logic.commands.find;

import java.util.List;
import java.util.ArrayList;
import eggventory.ui.TableStruct;
import eggventory.ui.Ui;
import eggventory.model.items.StockType;
import eggventory.model.items.Stock;
import eggventory.logic.commands.Command;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.commons.enums.CommandType;

//@@author yanprosobo
/**
 * Command objects for searching for stocks by filter then the search query.
 */
public class FindDescriptionCommand extends Command {
    private String search;

    public FindDescriptionCommand(CommandType type, String search) {
        super(type);
        this.search = search;
    }

    /**
     * Allows the user to search for stock descriptions that match a given string.
     * Prints the list of stocks that matches. Alternatively prints a message if none are found.
     */
    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        String output;
        int stockTypeQuantity = list.getStockTypeQuantity();
        boolean found = false;

        //for UI
        ArrayList<Stock> findList = new ArrayList<>();

        //for GUI
        TableStruct tableStruct = new TableStruct("Query for: " + search);
        tableStruct.setTableColumns("Stock Type", "Stock Code", "Quantity", "Description");
        ArrayList<ArrayList<String>> dataArray = new ArrayList<>();

        //for each stocktype
        for (int i = 0; i < stockTypeQuantity; i++) {
            ArrayList<Stock> uiSubList = new ArrayList<>();
            StockType currStockType = list.get(i);
            uiSubList = currStockType.queryAllStocksDescription(search);
            findList.addAll(uiSubList);
        }

        if (!findList.isEmpty()) {
            found = true;
        }

        if (!found) {
            output = "Sorry, I could not find any stock containing the description \""
                    + search + "\".\nPlease try a different search string.";
            ui.print(output);
        } else {
            //Format and prints ui
            StringBuilder ret = new StringBuilder();
            int i = 1;
            for (Stock stock : findList) {
                ret.append(String.format("%d. ", i++)).append(stock.toString()).append("\n");
            }
            output = ret.toString();
            ui.print(output);

            //Format and prints gui
            for (Stock stock: findList) {
                ArrayList<String> subDataArray = new ArrayList<>();;
                subDataArray = stock.getDataAsArray();
                dataArray.add(subDataArray);
            }
            tableStruct.setTableData(dataArray);
            ui.drawTable(tableStruct);
        }
        return output;
    }
}
//@@author
