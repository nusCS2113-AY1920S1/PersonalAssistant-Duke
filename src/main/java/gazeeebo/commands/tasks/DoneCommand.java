package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class DoneCommand extends Command {
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<String> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        try {
            if (ui.fullCommand.equals("done")) {
                throw new DukeException("The task done number cannot be empty.");
            }
            int numbercheck = Integer.parseInt(ui.fullCommand.substring(5)) - 1;
            list.get(numbercheck).isDone = true;

            System.out.println("Nice! I've marked this task as done: ");
            System.out.println(list.get(numbercheck).listFormat());

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).description.contains(list.get(numbercheck).description) && list.get(i).listFormat().contains("/after") && i != numbercheck) {
                    System.out.println("OK! Now you need to do the following:");
                    String[] temp = list.get(i).listFormat().split("\\(/after");
                    System.out.println(temp[0].substring(7));
                }
            }
            /**
             * Add some recurring task
             */

            RecurringCommand rc = new RecurringCommand();
            rc.addRecurring(list, numbercheck, list.get(numbercheck).toString(), storage);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.Storages(sb.toString());

            ui.showProgessiveBar(list);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void undo(final String command, final ArrayList<Task> list, final Storage storage) throws IOException {
        int numberCheck = Integer.parseInt(command.substring(5)) - 1;
        list.get(numberCheck).isDone = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.Storages(sb.toString());
        System.out.println("Nice! I've undo this command" + command);
    }
    @Override
    public boolean isExit() {
        return false;
    }
}