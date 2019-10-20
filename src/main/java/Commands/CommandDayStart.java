package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class CommandDayStart extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        Simulation.animate(ui, storage, farmio.getFarmer(), "DayStart", 1, 5);
        ui.show("Press ENTER to start the day!");
        farmio.setStage(Farmio.Stage.RUNNING_DAY);
    }
}
