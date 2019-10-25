package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

public class CloseCommand extends Command {
    public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {
        storage.saveToFile(resources.getResources());
        ui.farewell();
        setExitCode();
    }
}