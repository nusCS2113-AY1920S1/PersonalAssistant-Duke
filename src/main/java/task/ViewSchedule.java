package task;

import java.util.ArrayList;

import command.Command;
import storage.Storage;
import ui.Ui;

/**
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