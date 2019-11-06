package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
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
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        storage.storeFarmer(farmer);
        Level level = new Level(storage.getLevel(farmer.getLevel()),farmer.getName());
        farmio.setLevel(level);
        farmio.getUi().showNarrative(level, farmio.getSimulation());
        farmio.setStage(Farmio.Stage.TASK_ADD);
    }
}
