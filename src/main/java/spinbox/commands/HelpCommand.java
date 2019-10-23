package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.HelpList;
import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;
import spinbox.Ui;

import java.util.ArrayDeque;

public class HelpCommand extends Command {
    private static final String COMMAND_NOT_FOUND = "The command specified is not a valid command. Type 'help' to view"
            + " the full list of commands available.";
    private String specificCommand;

    public HelpCommand(String content) {
        this.specificCommand = content;
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {
        HelpList helpText = new HelpList();
        String helpOutput;
        if (specificCommand.equals("")) {
            helpOutput = helpText.helpOnly;
        } else {
            switch (specificCommand) {
            case "view":
                helpOutput = helpText.view;
                break;
            case "add":
                helpOutput = helpText.add;
                break;
            case "remove":
                helpOutput = helpText.remove;
                break;
            case "remove-multiple":
                helpOutput = helpText.removeMultiple;
                break;
            case "update":
                helpOutput = helpText.update;
                break;
            case "update-multiple":
                helpOutput = helpText.updateMultiple;
                break;
            default:
                throw new InputException(COMMAND_NOT_FOUND);
            }
        }
        return helpOutput;
    }
}
