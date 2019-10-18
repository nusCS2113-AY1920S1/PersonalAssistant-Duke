package gazeeebo.commands;

import gazeeebo.storage.Storage;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import gazeeebo.tasks.Task;
import gazeeebo.tasks.DoAfter;

public class DoAfterCommand extends Command {
    /**
     *
     * @param list task lists
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws ParseException
     * @throws IOException
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<String> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        String before = "";
        String after = "";
        String[] splitstring = ui.fullCommand.split("/after");
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
    public void undo(final String command, final ArrayList<Task> list, final Storage storage) throws IOException {
        String before = "";
        String after = "";
        String[] splitstring = command.split("/after");
        before = splitstring[1];
        after = splitstring[0];
        for (Task it : list) {
            if (it.listFormat().contains(after + "(/after:" + before + ")")) {
                list.remove(it);
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.Storages(sb.toString());
    }
    /**
     * Tells the main Duke class that the system should not exit and continue running.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}