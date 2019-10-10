package commands.expense;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import commands.Command;
import Exception.DukeException;
import expenses.Dining;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DiningCommand extends Command {

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws IOException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String purchaseDescription;
        String on = ui.FullCommand.split("@")[1];
        try {
            if(!ui.FullCommand.contains("$")
                    || !ui.FullCommand.split("$")[1].equals(currencyFormat)) {
                throw new DukeException("Expenses must be in the right format, e.g. $5.10");

            } else if(!ui.FullCommand.contains("@") || !ui.FullCommand.split("@")[1].equals(fmt)) {
                throw new DukeException("Date of spending must be in yyyy-MM-dd format.");
            } else {
                purchaseDescription = ui.FullCommand.split("@")[0];
            }

            Dining dn = new Dining(purchaseDescription, on);
            LocalDate dateOfPurchase = LocalDate.parse(on, fmt);
            ArrayList<String> expenses = new ArrayList<String>(); //total list of overall expenses
            ArrayList<String> diningList = new ArrayList<>(); //total list of dining expenses

                HashMap<LocalDate, ArrayList<String>> expensesMap = new HashMap<>(); //hashmap to store date as key and expenses list as values

                String itemAndPrice = ui.FullCommand.split("@")[0].trim();
                expenses.add(itemAndPrice);

                for(Map.Entry i : expensesMap.entrySet()) {
                    if(dateOfPurchase.equals(i.getKey())) {
                        expenses.add(itemAndPrice);
                        diningList.add(ui.FullCommand);
                        expensesMap.put(dateOfPurchase, expenses);
                    } else if (!dateOfPurchase.equals(i.getKey())) {
                        expensesMap.put(dateOfPurchase, expenses);
                    }
                }

                System.out.println("Got it. I've added this task:");
                System.out.println(dn.toString());
                System.out.println("Now you have " + diningList.size() + " tasks in the list.");

        } catch (DukeException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException a) {

        }
    }
            @Override
        public boolean isExit () {
            return false;
        }
}

