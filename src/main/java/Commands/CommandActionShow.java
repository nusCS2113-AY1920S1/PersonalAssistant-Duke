package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;

public class CommandActionShow extends Command {
    /**
     * Shows a list of Actions the user can take
     * @param farmio the game where the level is extracted to show only the relevant actions
     * @throws FarmioFatalException if Simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        if ((int)farmio.getFarmer().getLevel() == 1) {
            farmio.getSimulation().simulate("ActionList", 1);
        } else if ((int)farmio.getFarmer().getLevel() == 2) {
            farmio.getSimulation().simulate("ActionList", 2);
        } else if ((int)farmio.getFarmer().getLevel() == 3) {
            farmio.getSimulation().simulate("ActionList", 3);
        }
        ui.show("Press [Enter] to go back");
    }
}