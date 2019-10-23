package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Level;
import Farmio.Farmer;
import Farmio.Storage;

public class CommandLevelEnd extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        farmio.getUi().typeWriter("Farmer John is now ready for his next adventure!\nPress [ENTER] to begin the next level!");
        //TODO implement Level.nextLevel()
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        Level level = new Level(storage.getLevel(farmer.nextLevel()), farmio.getFarmer());
        farmio.setLevel(level);
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}