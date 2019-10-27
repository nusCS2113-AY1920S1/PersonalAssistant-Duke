package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;
import Farmio.Storage;
import Farmio.Farmer;
import Farmio.Level;

public class CommandLevelStart extends Command {
    /**
     * Starts the level
     * @param farmio the game where level is extracted from for narratives
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        Level level = new Level(storage.getLevel(farmer.getLevel()));
        farmio.setLevel(level);
        int frameId = 0;
        for(String narrative: level.getNarratives()){
            String userInput = ui.getInput();
            if (!userInput.equals("")) {
                farmio.getSimulation().simulate(level.getPath(), level.getNarratives().size() - 1);
                break;
            }
            farmio.getSimulation().simulate(level.getPath(), frameId++);
            ui.typeWriter(narrative, true);
        }
        farmio.setStage(Farmio.Stage.TASK_ADD);
    }
}
