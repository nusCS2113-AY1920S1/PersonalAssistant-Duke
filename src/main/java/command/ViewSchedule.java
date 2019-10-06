package command;

import java.util.ArrayList;

import command.Command;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * Represents a command from the user to view their schedule.
 * Inherits from the Command class.
 * @author Ng Jian Wei
 * */
public class ViewSchedule extends Command {

    protected String searchPhrase;

    public ViewSchedule(String query) {
        searchPhrase = query;
    }

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        ArrayList<Task> foundItems = tasks.findAllDates(searchPhrase);
        ui.showFound(foundItems);
    }
}