package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Level;
import farmio.Farmer;
import farmio.Storage;

public class CommandLevelEnd extends Command {
    /**
     * Ends the Level and calls the next level.
     * @param farmio the game which stage is reset to LEVEL_START.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        farmio.getUi().typeWriter("Farmer + is now ready for his next adventure! "
                + "Press [ENTER] for the narrative or enter [SKIP] to skip the story", false);
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        Level level = new Level(storage.getLevel(farmer.nextLevel()),farmer.getName());
        storage.storeFarmer(farmer);
        farmio.setLevel(level);
        farmio.getFarmer().getTasks().clear();
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}