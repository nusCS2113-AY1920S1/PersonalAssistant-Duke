package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Ui;

public class CommandDayEnd extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        farmio.getSimulation().animate("DayEnd", 0, true);
        farmio.getUi().show("Press ENTER to start your next day!");
        //TODO implement farmer.nextDay
        farmio.setStage(Farmio.Stage.DAY_START);
    }
}
