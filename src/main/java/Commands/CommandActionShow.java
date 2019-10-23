package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;

public class CommandActionShow extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        if ((int)farmio.getFarmer().getLevel() == 1) {
            farmio.getSimulation().animate("ActionList", 1);
        } else if ((int)farmio.getFarmer().getLevel() == 2) {
            farmio.getSimulation().animate("ActionList", 2);
        } else if ((int)farmio.getFarmer().getLevel() == 3) {
            farmio.getSimulation().animate("ActionList", 3);
        }
        ui.show("Press [Enter] to go back");
    }
}