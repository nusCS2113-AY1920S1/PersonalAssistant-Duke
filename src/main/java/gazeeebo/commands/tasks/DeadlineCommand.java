package gazeeebo.commands;

import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.io.IOException;

import gazeeebo.tasks.*;
import gazeeebo.exception.DukeException;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

public class DeadlineCommand extends Command {
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<String> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        String description;
        try {
            if (ui.fullCommand.length() == 8) {
                throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
            } else {
                description = ui.fullCommand.split("/by ")[0].substring(9);
                triviaManager.learnInput(ui.fullCommand, storage);
            }
            Deadline d = new Deadline(description, ui.fullCommand.split("/by ")[1]);
            list.add(d);
            System.out.println("Got it. I've added this task:");
            System.out.println(d.listFormat());
            System.out.println("Now you have " + list.size() + " tasks in the list.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.Storages(sb.toString());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            triviaManager.showPossibleInputs("deadline");
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException a) {
            Ui.showDeadlineDateFormatError();
        }
    }
    public void undo(final String command, final ArrayList<Task> list, final Storage storage) throws IOException {
        for (Task it : list) {
            if (it.description.contains(command.substring(9).trim())) {
                list.remove(it);
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.Storages(sb.toString());
    }
    @Override
    public boolean isExit() {
        return false;
    }
}