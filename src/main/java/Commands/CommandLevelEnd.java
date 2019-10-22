package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;

public class CommandLevelEnd extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        farmio.getUi().typeWriter("Farmer John is now ready for his next adventure!\nPress [ENTER] to begin the next level!");
        //TODO implement Level.nextLevel()
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
