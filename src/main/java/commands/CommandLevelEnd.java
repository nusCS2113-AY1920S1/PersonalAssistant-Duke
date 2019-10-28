package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Level;
import farmio.Farmer;
import farmio.Storage;

public class CommandLevelEnd extends Command {
    /**
     * Ends the Level and calls the next level
     * @param farmio the game which stage is reset to LEVEL_START
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        farmio.getUi().typeWriter("Farmer John is now ready for his next adventure! Press [ENTER] to begin the next level!", false);
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        Level level = new Level(storage.getLevel(farmer.nextLevel()));
        storage.storeFarmer(farmer);
        farmio.setLevel(level);
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}