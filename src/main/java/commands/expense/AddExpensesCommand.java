package commands.expense;

import Storage.Storage;
import UI.Ui;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddExpensesCommand {
public AddExpensesCommand (Ui ui, Storage storage, Map<LocalDate,ArrayList<String>> expenses) throws IOException{

        System.out.println("What did you buy:");
        ui.ReadCommand();
        String item = ui.FullCommand;

        System.out.println("How much did you spend:");
        ui.ReadCommand();
        String price = ui.FullCommand;

        String itemAndPrice = item + " " + price;

        System.out.println("Date of purchase:");
        ui.ReadCommand();
        String date = ui.FullCommand;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfPurchase = LocalDate.parse(date, fmt);

        ArrayList<String> itemAndPriceList = new ArrayList<>();

        boolean isEqual = false;
        for(LocalDate key : expenses.keySet()) {
                if (dateOfPurchase.equals(key)) { //if date equal
                        expenses.get(key).add(itemAndPrice);
                        isEqual = true;
                }
        }

        if (isEqual == false) {
                itemAndPriceList.add(itemAndPrice);
                expenses.put(dateOfPurchase, itemAndPriceList);
        }


        System.out.println("Okay we have successfully added your new expense: " + "\n" + itemAndPrice + " " + dateOfPurchase);

        String toStore = "";
        for (LocalDate key : expenses.keySet()) {
                if (expenses.get(key).size() > 1) {
                        for (int i = 0; i < expenses.get(key).size(); i++) {
                                toStore = toStore.concat(key + "|" + expenses.get(key).get(i) + "\n");
                        }
                } else if (expenses.get(key).size() == 1) {

                        toStore = toStore.concat(key + "|" + expenses.get(key).get(0) + "\n");
                }
        }
        storage.Storages_Expenses(toStore);
    }
}
