package gazeeebo.commands.help;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.Command;
import gazeeebo.help.HelpText;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class HelpCommand extends Command {

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        //gazeeebo.help COMMAND or just gazeeebo.help
        //description of a gazeeebo.help can be empty
        HelpText help = new HelpText();
        String description;
        String[] command = ui.FullCommand.split(" ");
        assert command.length != 0 : "Bug in parser that affects HelpCommand";
        if (command.length == 1) {
            description = help.commandFormat + System.lineSeparator() + System.lineSeparator() +
                    help.commandsHeader + help.commandSeparator +
                    help.todo + help.commandSeparator +
                    help.deadline + help.commandSeparator +
                    help.event + help.commandSeparator +
                    help.list + help.commandSeparator +
                    help.delete + help.commandSeparator +
                    help.done;
        } else {
            switch (command[1]) {
            case "todo": description = help.todo;
                break;
            case "deadline": description = help.deadline;
                break;
            case "event": description = help.event;
                break;
            case "list": description = help.list;
                break;
            case "delete": description = help.delete;
                break;
            case "done": description = help.done;
                break;
            default:
                description = "OOPS!!! There is no such command.";
                break;
            }
        }
        System.out.println(description);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
