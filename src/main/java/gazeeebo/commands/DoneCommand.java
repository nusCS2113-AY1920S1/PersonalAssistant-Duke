package gazeeebo.commands;

import gazeeebo.Tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.Storage.Storage;
import gazeeebo.Exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class DoneCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        try {
            if (ui.FullCommand.equals("done")) {
                throw new DukeException("The task done number cannot be empty.");
            }
            int numbercheck = Integer.parseInt(ui.FullCommand.substring(5)) - 1;
            list.get(numbercheck).isDone = true;

            System.out.println("Nice! I've marked this task as done: ");
            System.out.println(list.get(numbercheck).listFormat());


            /**
             * Print out the task to do after
             */
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
            rc.AddRecurring(list, numbercheck, list.get(numbercheck).toString(), storage);

            /**
             * Filter out those task that are done
             */


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
    public void undo(String command, ArrayList<Task> list, Storage storage) throws IOException {
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