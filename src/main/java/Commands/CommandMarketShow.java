package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;

public class CommandMarketShow extends Command {
    /**
     * Shows a list of the Market rates
     * @param farmio the game where the level is extracted to show only the relevant items
     * @throws FarmioFatalException if Simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        if ((int)farmio.getFarmer().getLevel() == 1) {
            farmio.getSimulation().simulate("MarketList", 1);
        } else if ((int)farmio.getFarmer().getLevel() == 2) {
            farmio.getSimulation().simulate("MarketList", 2);
        } else if ((int)farmio.getFarmer().getLevel() == 3) {
            farmio.getSimulation().simulate("MarketList", 3);
        }
        ui.show("Press [Enter] to go back");
    }
}