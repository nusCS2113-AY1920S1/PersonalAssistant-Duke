package commands;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import Exception.DukeException;
import help.Help;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class HelpCommand extends Command {

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
        //help COMMAND
        Help help = new Help();
        String description;
        String commandWord;
        try {
            commandWord = ui.FullCommand.split(" ")[1];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("OOPS!!! The description of a help cannot be empty.");
            return;
        }
        switch (commandWord) {
        case "todo": description = help.addingATodo();
            break;
        default: description = "OOPS!!! There is no such command.";
            break;
        }
        System.out.println(description);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
