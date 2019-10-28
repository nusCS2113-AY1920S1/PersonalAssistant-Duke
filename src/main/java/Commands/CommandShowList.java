package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;

public class CommandShowList extends Command {
    private String filePath;
    public CommandShowList(String listPath) {
       filePath = listPath;
    }
    /**
     * Shows a list from a file to the user
     * @param farmio the game where the level is extracted to show only the relevant actions
     * @throws FarmioFatalException if Simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        farmio.getSimulation().simulate(filePath, (int)farmio.getFarmer().getLevel());
        ui.show("Press [Enter] to go back");
    }
}