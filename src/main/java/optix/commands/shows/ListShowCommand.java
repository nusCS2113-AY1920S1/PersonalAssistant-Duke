package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;

public class ListShowCommand extends Command {
    private String showName;

    private static final String MESSAGE_FOUND_SHOW = "The show %1$s is showing on the following following dates: \n";

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show cannot be found.\n";

    public ListShowCommand(String showName) {
        this.showName = showName;
    }


    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder(String.format(MESSAGE_FOUND_SHOW, showName));

        message.append(model.listShow(showName));

        if (!hasShow(message.toString())) {
            message = new StringBuilder(MESSAGE_SHOW_NOT_FOUND);
        }

        ui.setMessage(message.toString());
        return "show";
    }

    @Override
    public String[] parseDetails(String details) {
        return details.split(" ");
    }



    private boolean hasShow(String message) {
        return !message.equals(String.format(MESSAGE_FOUND_SHOW, showName));
    }
}
