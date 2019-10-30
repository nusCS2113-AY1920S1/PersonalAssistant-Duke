package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.AsciiColours;
import frontend.Ui;
import farmio.Storage;
import farmio.Farmer;
import farmio.Level;

public class CommandLevelStart extends Command {
    /**
     * Starts the level.
     * @param farmio the game where level is extracted from for narratives.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        storage.storeFarmer(farmer);
        Level level = new Level(storage.getLevel(farmer.getLevel()),farmer.getName());
        farmio.setLevel(level);
        int frameId = 0;
        for (String narrative: level.getNarratives()) {
            String userInput = ui.getInput();
            if (userInput.equals("skip")) {
                farmio.getSimulation().simulate(level.getPath(), level.getNarratives().size() - 1);
                ui.showPointers(level.getHint());
                break;
            }
            farmio.getSimulation().simulate(level.getPath(), frameId++);
            ui.typeWriter(narrative, true);
        }
        farmio.setStage(Farmio.Stage.TASK_ADD);
    }
}
