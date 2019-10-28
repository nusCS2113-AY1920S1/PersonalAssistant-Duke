package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Farmer;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Ui;

public class CommandDayEnd extends Command {
    /**
     * Sets and shows the end of day and sets the next day
     * @param farmio the game where stage is changed to DAY_START
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        farmio.getSimulation().simulate("DayEnd", 0);
        farmer.nextDay();
        farmio.getSimulation().simulate(1000,"DayStart", 1);
        farmio.setStage(Farmio.Stage.DAY_START);
    }
}
