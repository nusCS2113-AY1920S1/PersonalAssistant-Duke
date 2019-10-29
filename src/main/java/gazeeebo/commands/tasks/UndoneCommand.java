package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class UndoneCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException {

        if (ui.fullCommand.equals("undo")) {
            throw new DukeException("The undo task number cannot be empty.");
        }

        int numberCheck = Integer.parseInt(ui.fullCommand.substring(5)) - 1;
        if (list.get(numberCheck).isDone == true) {
            list.get(numberCheck).isDone = false;
        }

        System.out.println("Nice! I've marked this task as undone: ");
        System.out.println(list.get(numberCheck).listFormat());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.writeToSaveFile(sb.toString());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}