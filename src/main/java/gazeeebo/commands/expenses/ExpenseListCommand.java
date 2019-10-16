package gazeeebo.commands.expenses;

import gazeeebo.UI.Ui;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import java.util.ArrayList;

public class ExpenseListCommand {

    /**
     * This method prints the list of all the expenses
     *
     * @param ui the object that deals with printing things to the user.
     * @param expenses the object that map each expenses to its date
     * @throws IOException catch any error if read file fails
     */
    public ExpenseListCommand (Ui ui, Map<LocalDate,ArrayList<String>> expenses) throws IOException{
        ArrayList<String> expenseList = new ArrayList<>();

        System.out.println("Here is the list of your expenses:");
        for (LocalDate key : expenses.keySet()) {
            for (int i = 0; i < expenses.get(key).size(); i++) {
                expenseList.add(expenses.get(key).get(i) + " | bought on " + key);
            }
        }

        for (int j = 0; j < expenseList.size(); j++) {
            System.out.println((j + 1) + ". " + expenseList.get(j));
        }

    }
}