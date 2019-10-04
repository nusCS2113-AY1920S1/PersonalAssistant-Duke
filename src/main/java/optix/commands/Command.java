package optix.commands;

import optix.Ui;
import optix.core.Storage;
import optix.util.ShowMap;

public abstract class Command {
    protected boolean quit;

    /**
     * Processes user input to be stored, queried, modified in ShowMap,
     * to show response by program in Ui and store existing data in Storage.
     * @param shows The list of performance that are upcoming.
     * @param ui The User Interface that reads user input and response to user.
     * @param storage The filepath of txt file which data are being stored.
     */
    public abstract void execute(ShowMap shows, Ui ui, Storage storage);


    public boolean isExit() {
        return false;
    }
}
