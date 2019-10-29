package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Ui;

public class CommandWelcome extends Command {

    /**
     * Show Welcome message.
     * @param farmio the game where ui is extracted from.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        farmio.getSimulation().simulate("Welcome", 1,true);
        ui.show("Press ENTER to continue.");
    }
}
