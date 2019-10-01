package optix.commands;

import optix.Ui;
import optix.constant.OptixResponse;
import optix.core.Storage;
import optix.util.ShowMap;

public class ByeCommand extends  Command {
    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        storage.write(shows);
        ui.setMessage(new OptixResponse().BYE);
        ui.exitOptix();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
