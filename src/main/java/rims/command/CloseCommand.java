package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

public class CloseCommand extends Command {

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {
        storage.saveToFile(resources.getResourceList());
        ui.farewell();
        setExitCode();
    }
}