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
        Level level = new Level(storage.getLevel(1.1), farmio.getFarmer());
        farmio.setLevel(level);
        farmio.getSimulation().animate("GameNew", 0 );
        ui.getInput();
        int frameId = 0;
        for(String narrative: level.getNarratives()){
            farmio.getSimulation().animate("Level1-01", frameId++);
            ui.show(narrative);
            ui.getInput();
        }
        farmio.setStage(Farmio.Stage.TASK_ADD);
    }
}
