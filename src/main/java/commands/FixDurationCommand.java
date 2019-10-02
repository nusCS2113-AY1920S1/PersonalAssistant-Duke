package commands;

import Storage.Storage;
import Tasks.FixedDuration;
import Tasks.Task;
import UI.Ui;

import java.io.IOException;

import Exception.DukeException;

import java.text.ParseException;
import java.util.ArrayList;

public class FixDurationCommand extends Command {
    /**
     * @param list    task list
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data to the Save.txt file.
     * @throws IOException
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws IOException, NullPointerException {
        String description = "";
        String duration = "";
        String[] splitstring;
        splitstring = ui.FullCommand.split("/requires");
        description = splitstring[0];
        duration = splitstring[1];

        FixedDuration to = new FixedDuration(description, duration);
        list.add(to);
        System.out.println("Got it. I've added this task:");
        System.out.println(to.listFormat());
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.Storages(sb.toString());
    }

    /**
     * Tells the main Duke class that the system should not exit and continue running
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

}