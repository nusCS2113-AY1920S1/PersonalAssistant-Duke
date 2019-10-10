package commands.expense;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import commands.Command;

import Exception.DukeException;
import expenses.ExpenseList;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class ExpenseListCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Your Expenses: \n");
        System.out.println("Dining Expenses:");
        for (int i = 0; i < ExpenseList.diningList.size(); i++) {
            System.out.println(i + 1 + "." + list.get(i).listFormat());
            System.out.println(" There are" +  ExpenseList.diningList.size() + "expenses");
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }

}