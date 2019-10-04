package optix.commands;

import optix.Ui;
import optix.constant.OptixResponse;
import optix.core.Storage;

import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;
import optix.util.ShowMap;

/**
 * Prompts user on the correct Command Input format.
 */
public class HelpCommand extends Command {
    private String command;
    private OptixResponse response = new OptixResponse();

    public HelpCommand() {
        command = "";
    }

    public HelpCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        try {
            if (command.equals("")) {
                ui.setMessage(response.MENU);
            } else {
                ui.setMessage(getSpecificCommand(command));
            }
        } catch (OptixException e) {
            ui.setMessage(e.getMessage());
        }

    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }

    /**
     * String message to prompt user of correct Command input format.
     * @param command The command in query.
     * @return String message for Command input format.
     * @throws OptixInvalidCommandException if user command input does not exist in Optix.
     */
    private String getSpecificCommand(String command) throws OptixInvalidCommandException {
        StringBuilder message = new StringBuilder("Valid " + command + " command:\n");

        switch (command.toLowerCase()) {
        case "add":
            message.append(response.ADD_MENU);
            break;
        case "delete":
            message.append(response.DELETE_MENU);
            break;
        case "view":
            message.append(response.VIEW_MENU);
            break;
        case "sell":
            message.append(response.SELL_MENU);
            break;
        case "list":
            message.append(response.LIST_MENU);
            break;
        case "postpone":
            message.append(response.POSTPONE_MENU);
            break;
        default:
            throw new OptixInvalidCommandException();
        }

        return message.toString();
    }

}
