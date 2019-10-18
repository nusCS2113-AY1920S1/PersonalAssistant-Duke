package gazeeebo.commands;


import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;

import gazeeebo.exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class TagCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        try {
            if (ui.fullCommand.equals("#") || ui.fullCommand.equals("# ")) {
                throw new DukeException("The tag description cannot be empty.");
            } else {
                ArrayList<Task> tagList = new ArrayList<>();
                String tag = ui.fullCommand.substring(1);
                for (Task it : list) {
                    if (it.description.contains("#")) {
                        if (it.description.split("#")[1].trim().equals(tag)) {
                            tagList.add(it);
                        }
                    }
                }
                System.out.println("Here are the matching tags in your list:");
                for (int i = 0; i < tagList.size(); i++) {
                    System.out.println(i + 1 + "." + tagList.get(i).listFormat());
                }
            }
        }
        catch(DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
