package commands.expense;

import UI.Ui;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class FindExpenseCommand {
    public FindExpenseCommand(Ui ui, Map<LocalDate, ArrayList<String>> expenses) throws IOException, NullPointerException {
        System.out.println("Please enter the date of expenses are you searching for:");
        ui.ReadCommand();
        String date = ui.FullCommand;
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
            System.out.println("Doesn't Exist in this world LOL hehe:)");
        }
//            System.out.println("Here are the expenses found:");
//            for (int i = 0; i < findList.size(); i++) {
//                System.out.println(i + 1 + "." + findList.get(i));
//            }

    }
}


