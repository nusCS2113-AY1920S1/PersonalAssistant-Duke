package gazeeebo.commands.expenses;

import gazeeebo.Storage.Storage;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class DeleteExpenseCommand {

    /**
     * This method deletes the expense from the expense list and expenses map.
     *
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data, in this case storing data in the expenses map
     * @param expenses the object that map each expenses to its date
     * @throws IOException catch any error if read file fails
     */
    public DeleteExpenseCommand(Ui ui, Storage storage, Map<LocalDate, ArrayList<String>> expenses) throws IOException {
        ArrayList<String> expenseList = new ArrayList<>();
        System.out.println("Enter the expense index that you wish to delete:");

        /* Prints list of expenses*/
        for (LocalDate key : expenses.keySet()) {
            for (int i = 0; i < expenses.get(key).size(); i++) {
                expenseList.add(expenses.get(key).get(i) + " | bought on " + key);
            }
        }
        for (int j = 0; j < expenseList.size(); j++) {
            System.out.println((j + 1) + ". " + expenseList.get(j));
        }

         /* Reading the expense index that is entered*/
        ui.ReadCommand();
        String expenseIndex = ui.FullCommand;
        int index = Integer.parseInt(expenseIndex) - 1;
        System.out.println("Expense " +  expenseList.get(index) + " deleted!");

        String getKeyFromList = expenseList.get(index).split(" ")[0];
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate getKey = LocalDate.parse(getKeyFromList, fmt);

        String getValueFromList = expenseList.get(index).split(" ")[1] + " " + expenseList.get(index).split(" ")[2];
        for(LocalDate key : expenses.keySet()) {

            if(key.equals(getKey)) {
                for(int i = 0; i < expenses.get(key).size(); i++) {
                    if(expenses.get(key).get(i).equals(getValueFromList)) {
                        expenses.get(key).remove(i);

                    }
                }
            }
        }

        expenseList.remove(index);

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
