package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class CommandDayEnd extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        Simulation.animate(ui, storage, "DayEnd", 0);
        farmio.getUi().show("Press ENTER to start your next day!");
        //farmer.nextLevel();
        farmio.setStage(Farmio.Stage.START_OF_DAY);
    }
}
