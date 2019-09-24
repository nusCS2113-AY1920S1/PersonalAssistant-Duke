package optix.commands;

import optix.Ui;
import optix.util.ShowMap;

public abstract class Command {
    protected boolean quit;

    public abstract void execute(ShowMap shows, Ui ui);

    public boolean isExit() {
        return false;
    }
}
