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
        Level level = new Level(storage.getLevel(1), farmio.getFarmer());
        farmio.setLevel(level);
        Simulation.animate(ui, storage, "GameNew", 0);
        ui.getInput();
        for(String narrative: level.getNarratives()){
            Simulation.animate(ui, storage, "Level1-01", 0);
            ui.show(narrative);
            ui.getInput();
        }
        farmio.setStage(Farmio.Stage.TASK_ADD);
    }
}
