package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Ui;

public class CommandShowList extends Command {
    private String filePath;

    public CommandShowList(String listPath) {
        filePath = listPath;
    }

    /**
     * Shows a list from a file to the user.
     * @param farmio the game where the level is extracted to show only the relevant actions.
     * @throws FarmioFatalException if Simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        double level = farmio.getFarmer().getLevel();
        farmio.getSimulation().simulate(filePath, (int)(level * 10),false);

        ui.show("Press [ENTER] to go back to game");
    }

}