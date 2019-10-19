package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Level;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class CommandGameNew extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        farmio.setFarmer(new Farmer());
        farmio.setLevel(new Level(storage.getLevel(1), farmio.getFarmer()));
        Simulation.animate(ui, storage, "GameNew", 0);
        farmio.setStage(Farmio.Stage.TASK_ADD);
    }
}
