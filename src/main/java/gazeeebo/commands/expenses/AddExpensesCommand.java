package gazeeebo.commands.expenses;

import gazeeebo.Storage.Storage;
import gazeeebo.UI.Ui;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddExpensesCommand {

    /**
     * This method adds the expense from the expense list and expenses.
     *
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data, in this case storing data in the expenses map
     * @param expenses the object that map each expenses to its date
     * @throws IOException catch any error if read file fails
     */
    public AddExpensesCommand (Ui ui, Storage storage, Map<LocalDate,ArrayList<String>> expenses) throws IOException{

        System.out.println("What did you buy:");
        ui.ReadCommand();
        String item = ui.FullCommand;

        System.out.println("How much did you spend:");
        ui.ReadCommand();
        String price = ui.FullCommand;

        String itemAndPrice = item + ", " + price;

        System.out.println("Date of purchase:");
        ui.ReadCommand();
        String date = ui.FullCommand;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfPurchase = LocalDate.parse(date, fmt);

        /*Storing expenses value as an array to the date keys. If the item were bought on the same date as a previous item,
          add the item in the array under the same key.*/
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
        System.out.println("Okay we have successfully added your new expense: " + "\n" + itemAndPrice + ", bought on " + dateOfPurchase);

        /*Stores the updated expenses map after deletion of expenses*/
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
