package commands.expense;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import commands.Command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExpenseCommand extends Command {
    private static String LINE_BREAK = "------------------------------------------\n";
    /**
     * This method is the list of all the contact numbers and you got add/find/delete contacts.
     * @param list list of all tasks
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws IOException Catch error if the read file fails
     */

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack,ArrayList<Task> deletedTask) throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = storage.Expenses(); //Read the file
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<LocalDate, ArrayList<String>>(map);
       // ArrayList<String> expenseList = storage.Expenses();
        //ArrayList<String> itemAndPriceList = new ArrayList<>();

        System.out.print("Welcome to your expenses record!\n\n");
        System.out.println("Here is the list of your expenses:");
        for(LocalDate key: expenses.keySet()) {
            for(int i = 0; i < expenses.get(key).size(); i++) {
                System.out.println(key + " " + expenses.get(key).get(i));
            }
        }


        ui.ReadCommand();
        while(!ui.FullCommand.equals("esc")) {
            if (ui.FullCommand.contains("add")) {
                new AddExpensesCommand(ui, storage, expenses);
            } else if(ui.FullCommand.equals("find")) {
                new FindExpenseCommand(ui, expenses);
            }
            ui.ReadCommand();
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
