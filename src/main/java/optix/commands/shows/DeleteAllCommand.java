package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;

public class DeleteAllCommand extends Command {
    private String[] showNames;

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
