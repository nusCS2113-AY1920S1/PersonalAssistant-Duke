package gazeeebo.commands.expenses;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class FindExpenseCommand {

    /**
     * This method finds expenses bought on the same date.
     *
     * @param ui the object that deals with printing things to the user.
     * @param expenses the object that map each expenses to its date
     * @throws IOException catch any error if read file fails
     */
    public FindExpenseCommand(Ui ui, Map<LocalDate, ArrayList<String>> expenses) throws IOException, NullPointerException {
        System.out.println("Please enter the date of expenses are you searching for:");
        ui.readCommand();
        String date = ui.fullCommand;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfPurchase = LocalDate.parse(date, fmt);
        boolean isExist = false;
        for(LocalDate key: expenses.keySet()) {
            if (dateOfPurchase.equals(key)) {
                System.out.println("Expenses found:");
                for (int i = 0; i < expenses.get(key).size(); i++) {
                    System.out.println((i+1) + "." + expenses.get(key).get(i));
                }
                isExist = true;
                break;
            }
        }
        if(!isExist) {
            System.out.println("Date does not exist!");
        }

    }
}


