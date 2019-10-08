package commands;

import Tasks.Task;
import UI.Ui;
import Storage.Storage;

import java.io.IOException;

import Tasks.*;
import exception.DukeException;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class DeadlineCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        String description;
        try {
            if (ui.FullCommand.length() == 8) {
                throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
            } else {
                description = ui.FullCommand.split("/by ")[0].substring(9);
            }
            Deadline d = new Deadline(description, ui.FullCommand.split("/by ")[1]);
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
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException a) {
            Ui.showDeadlineDateFormatError();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}