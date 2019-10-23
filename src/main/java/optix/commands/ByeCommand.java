package optix.commands;

import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;

public class ByeCommand extends Command {

    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!\n";

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        storage.write(model.getShows());
        ui.setMessage(MESSAGE_BYE);
        ui.exitOptix();
    }

    @Override
    public String[] parseDetails(String details) {
        return new String[0];
    }

    /**
     * Exits Optix.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
