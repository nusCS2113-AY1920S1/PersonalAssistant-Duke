package optix.commands;

import optix.Ui;
import optix.core.Storage;
import optix.util.ShowMap;

public abstract class Command {
    protected boolean quit;

    public abstract void execute(ShowMap shows, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }
}
