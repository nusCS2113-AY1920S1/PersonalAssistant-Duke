package gazeeebo.commands;

import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import gazeeebo.exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class UndoneListCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        ArrayList<Task> UndoneList = new ArrayList<>();
        try {
            if (ui.fullCommand.equals("undone")) {
                throw new DukeException("Command for 'undone' cannot be empty.");
            }

            for (Task task : list) {
                if (task.isDone == false) {
                    UndoneList.add(task);
                }
            }
            if (ui.fullCommand.equals("undone list")) {
                System.out.println("List of tasks that are undone:");
                for (int i = 0; i < UndoneList.size(); i++) {
                    System.out.println(i + 1 + "." + UndoneList.get(i).listFormat());
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.Storages(sb.toString());
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isExit () {
        return false;
    }
}
