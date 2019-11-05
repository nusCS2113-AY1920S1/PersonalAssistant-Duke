package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

public class HelpCommand extends Command {
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) {
        ui.help();
    }
}