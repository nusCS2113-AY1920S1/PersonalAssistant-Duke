package gazeeebo.commands;


import gazeeebo.Tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.Tasks.*;
import gazeeebo.Storage.Storage;
import gazeeebo.Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class TodoCommand extends Command {

    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<String> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        String description = "";
        try {
            if (ui.fullCommand.length() <= 4) {
                throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
            } else {
                description = ui.fullCommand.substring(5);
                triviaManager.learnInput(ui.fullCommand,storage);
        }
        Todo to = new Todo(description);
        list.add(to);
        System.out.println("Got it. I've added this task:");
        System.out.println(to.listFormat());
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.storages(sb.toString());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            triviaManager.showPossibleInputs("todo");
            //triviaManager.showAllMap();
        }
    }
    public void undo(final String command, final ArrayList<Task> list, final Storage storage) throws IOException {
        for (Task it : list) {
            if (it.description.contains(command.substring(6).trim())) {
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
    @Override
    public boolean isExit() {
        return false;
    }

}