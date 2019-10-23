package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;
import Farmio.Storage;
import Farmio.Farmer;
import Farmio.Level;

public class CommandLevelStart extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        Level level = new Level(storage.getLevel(farmer.getLevel()), farmio.getFarmer());
        farmio.setLevel(level);
        int frameId = 0;
        for(String narrative: level.getNarratives()){
            String userInput = ui.getInput();
            if (userInput.equals("skip")) {
                break;
            }
            farmio.getSimulation().animate(level.getPath(), frameId++);
            ui.typeWriter(narrative);
        }
        farmio.setStage(Farmio.Stage.TASK_ADD);
    }
}
