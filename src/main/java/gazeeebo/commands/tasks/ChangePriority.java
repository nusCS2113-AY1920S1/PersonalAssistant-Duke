package gazeeebo.commands.tasks;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class ChangePriority extends Command {
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {
        try {
            if (ui.fullCommand.equals("priority")) {
                throw new DukeException("The task number cannot be empty.");
            }
            int numbercheck = Integer.parseInt(
                    ui.fullCommand.split(" ")[1]) - 1;
            int numberpriority = Integer.parseInt(ui.fullCommand.split(" ")[2]);

            list.get(numbercheck).priority = numberpriority;
            System.out.println("The priority of the task"
                    + "is successfully changed!");
            System.out.println("Task " + list.get(numbercheck).description
                    + " now has priority " + list.get(numbercheck).priority);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.writeToSaveFile(sb.toString());

        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Program does not exit and continues running
     * since command "bye" is not called.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
