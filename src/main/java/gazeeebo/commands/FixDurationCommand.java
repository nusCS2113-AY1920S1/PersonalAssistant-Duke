package gazeeebo.commands;

import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.FixedDuration;
import gazeeebo.Tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;

import gazeeebo.Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class FixDurationCommand extends Command {
    /**
     * @param list    task list
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data to the Save.txt file.
     * @param commandStack
     * @param deletedTask
     * @throws IOException
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<String> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        String description = "";
        String duration = "";
        String[] splitstring;
        splitstring = ui.fullCommand.split("/require");
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
        storage.storages(sb.toString());
    }

    public void undo(final String command, final ArrayList<Task> list, final Storage storage) throws IOException {
        for (Task it : list) {
            if (it.description.contains(command.split("/requires")[0].trim())) {
                list.remove(it);
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.storages(sb.toString());
    }
    /**
     * Tells the main Duke class that the system
     * should not exit and continue running.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

}