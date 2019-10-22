package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

public class HomeCommand extends Command {
    public void execute(Ui ui, Storage storage, ResourceList resources) {
        ui.Home();
        ui.printEmptyLine();
        ui.printLine();
    }
}