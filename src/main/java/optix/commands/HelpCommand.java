package optix.commands;

import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;

/**
 * Prompts user on the correct Command Input format.
 */
public class HelpCommand extends Command {
    private String command;

    private static final String ADD_MENU = "To add a new show:                            add SHOW_NAME|SCHEDULED_DATE|PRICE|SEATS_BASE_PRICE\n";

    private static String DELETE_MENU = "To delete shows with particular name:         delete-all SHOW_NAME_1|SHOW_NAME_2 | ...\n"
                                        + "To delete shows with specific name and date:  delete SHOW_NAME|SHOW_DATE\n";

    private static String VIEW_MENU = "To view availability of seats for a show:     view SHOW_NAME | SHOW_DATE\n";

    private static String SELL_MENU = "To sell seats to the audience:                sell SHOW_NAME | SHOW_DATE | BUYER_NAME\n";

    private static String LIST_MENU = "To list all shows for viewing:                list\n"
                                    + "To list all dates for a specific show:        list SHOW_NAME\n";

    private static String POSTPONE_MENU = "To postpone a show to a later date:           postpone SHOW_NAME | OLD_DATE | NEW_DATE\n";

    private static String EDIT_MENU = "To edit show name:                            edit OLD_SHOW_NAME | SHOW_DATE | NEW_SHOW_NAME\n";

    private static String MESSAGE_MENU = "Here are the Commands to use Optix: \n"
                                + ADD_MENU + DELETE_MENU + LIST_MENU + SELL_MENU + POSTPONE_MENU + VIEW_MENU + EDIT_MENU;

    public HelpCommand() {
        command = "";
    }

    public HelpCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        try {
            if (command.equals("")) {
                ui.setMessage(MESSAGE_MENU);
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
            message.append(ADD_MENU);
            break;
        case "delete":
            message.append(DELETE_MENU);
            break;
        case "view":
            message.append(VIEW_MENU);
            break;
        case "sell":
            message.append(SELL_MENU);
            break;
        case "list":
            message.append(LIST_MENU);
            break;
        case "postpone":
            message.append(POSTPONE_MENU);
            break;
        case "edit":
            message.append(EDIT_MENU);
            break;
        default:
            throw new OptixInvalidCommandException();
        }

        return message.toString();
    }

}
