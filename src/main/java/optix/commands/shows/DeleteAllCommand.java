package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;

public class DeleteAllCommand extends Command {
    private String[] showNames;

    private static final String MESSAGE_SUCCESSFUL = "Noted. These are the deleted entries:\n";

    private static final String MESSAGE_UNSUCCESSFUL = "Sorry, these shows were not found:\n";

    public DeleteAllCommand(String[] showNames) {
        this.showNames = showNames;
    }

    @Override
    /*
     * Command that deletes all instances of a specific show.
     */
    public void execute(Model model, Ui ui, Storage storage) {
        String message = model.deleteShow(showNames);

        ui.setMessage(message);
    }
}
