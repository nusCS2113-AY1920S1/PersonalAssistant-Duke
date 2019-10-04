package optix.commands;

import optix.Ui;
import optix.constant.OptixResponse;
import optix.core.Storage;
import optix.util.ShowMap;

public class ByeCommand extends  Command {

    private OptixResponse response = new OptixResponse();

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        storage.write(shows);
        ui.setMessage(response.BYE);
        ui.exitOptix();
    }

    /**
     * Exits Optix.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
