package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Farmer;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Ui;

public class CommandDayEnd extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        farmio.getSimulation().simulate(1000,"DayEnd", 0);
        farmer.nextDay();
        farmio.setStage(Farmio.Stage.DAY_START);
    }
}
