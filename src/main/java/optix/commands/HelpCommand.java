package optix.commands;

import optix.Ui;
import optix.constant.OptixResponse;
import optix.core.Storage;

import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;
import optix.util.ShowMap;

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


    private String getSpecificCommand(String command) throws OptixInvalidCommandException {
        StringBuilder message = new StringBuilder("Valid " + command + " command:\n");

        switch (command.toLowerCase()) {
        case "add":
            message.append("To add a new show:                            add SHOW_NAME|SCHEDULED_DATE|PRICE|SEATS_BASE_PRICE\n");
            break;
        case "delete":
            message.append("To delete shows with particular name:         delete-all SHOW_NAME_1|SHOW_NAME_2 | ...\n"
                    + "To delete shows with specific name and date:  delete SHOW_NAME|SHOW_DATE\n");
            break;
        case "view":
            message.append("To view availability of seats for a show:     view SHOW_NAME | SHOW_DATE\n");
            break;
        case "sell":
            message.append("To sell seats to the audience:                sell SHOW_NAME | SHOW_DATE | BUYER_NAME\n");
            break;
        case "list":
            message.append("To list all shows for viewing:                list\n"
                    + "To list all dates for a specific show:        list SHOW_NAME\n");
            break;
        case "postpone":
            message.append("To postpone a show to a later date:           postpone SHOW_NAME | OLD_DATE | NEW_DATE\n");
            break;
        default:
            throw new OptixInvalidCommandException();
        }

        return message.toString();
    }

}
