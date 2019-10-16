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

public class DoneListCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        ArrayList<Task> DoneList = new ArrayList<>();
        try {
            if (ui.fullCommand.equals("done")) {
                throw new DukeException("Command for 'done' cannot be empty.");
            }
            for (Task task : list) {
                if (task.isDone == true) {
                    DoneList.add(task);
                }
            }

            if (ui.fullCommand.equals("done list")) {
                System.out.println("List of tasks that are done:");
                for (int i = 0; i < DoneList.size(); i++) {
                    System.out.println(i + 1 + "." + DoneList.get(i).listFormat());
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.storages(sb.toString());
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
