package optix.commands;

import optix.Ui;
import optix.constant.OptixResponse;
import optix.core.Storage;
import optix.util.ShowMap;

public class HelpCommand extends Command {
    private OptixResponse response = new OptixResponse();

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        ui.setMessage(response.MENU);
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
