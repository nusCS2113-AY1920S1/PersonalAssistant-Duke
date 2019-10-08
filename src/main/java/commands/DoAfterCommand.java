package commands;

import Storage.Storage;
import Tasks.DoAfter;
import Tasks.Task;
import UI.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class DoAfterCommand extends Command {
    /**
     *
     * @param list      task lists
     * @param ui        the object that deals with printing things to the user.
     * @param storage   the object that deals with storing data.
     * @throws ParseException
     * @throws IOException
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws ParseException, IOException, NullPointerException {
        String before = "";
        String after = "";
        String[] splitstring = ui.FullCommand.split("/after");
        before = splitstring[1];
        after = splitstring[0];
        DoAfter to = new DoAfter(before, before, after);
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