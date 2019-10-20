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
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        try {
            if (ui.fullCommand.equals("priority")) {
                throw new DukeException("The task number cannot be empty.");
            }
            int numbercheck = Integer.parseInt(ui.fullCommand.split(" ")[1])-1;
            int numberpriority = Integer.parseInt(ui.fullCommand.split(" ")[2]);

            list.get(numbercheck).priority = numberpriority;
            System.out.println("The priority of the task is successfully changed!");
            System.out.println("Task "+list.get(numbercheck).description+" now has priority "+list.get(numbercheck).priority);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.Storages(sb.toString());

        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
//    public void undo(String command, ArrayList<Task> list, Storage storage) throws IOException {
//        int numberCheck = Integer.parseInt(command.substring(5)) - 1;
//        list.get(numberCheck).isDone = false;
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            sb.append(list.get(i).toString() + "\n");
//        }
//        storage.Storages(sb.toString());
//        System.out.println("Nice! I've undo this command" + command);
//    }
    @Override
    public boolean isExit() {
        return false;
    }
}
