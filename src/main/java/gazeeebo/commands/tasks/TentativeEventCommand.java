package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import gazeeebo.exception.DukeException;
import gazeeebo.tasks.TentativeEvent;

import java.io.IOException;

import java.text.ParseException;
import java.util.ArrayList;

import java.util.Stack;

public class TentativeEventCommand extends Command {
    @Override
    public void execute(final ArrayList<Task> list,
                       final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {
        String description = "";
        try {
            if (ui.fullCommand.length() == 9) {
                throw new DukeException("OOPS!!! The description of an "
                        + "tentative event cannot be empty.");
            } else {
                description = ui.fullCommand.substring(10);
                System.out.println("You are creating "
                        + "a tentative event: " + description);
                System.out.println("Please enter possible time "
                        + "slots of the event");
                System.out.println("When you are done, key in '/'.");
                ArrayList<String> tentativetimes = new ArrayList<String>();
                ui.readCommand();
                while (!ui.fullCommand.equals("/")) {
                    tentativetimes.add(ui.fullCommand);
                    ui.readCommand();
                }
                TentativeEvent newTentative
                        = new TentativeEvent(description, tentativetimes);
                System.out.println("Got it. I've added this tentative event:");
                System.out.println(newTentative.listFormat());
                System.out.println("You could confirm one of the slots later.");
                list.add(newTentative);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.writeToSaveFile(sb.toString());
            }
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
