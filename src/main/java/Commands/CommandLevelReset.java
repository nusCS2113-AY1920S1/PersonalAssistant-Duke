package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;

public class CommandLevelReset extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        //TODO implement level.reset to reset the stage in case user types some wrong thing
        farmio.getUi().typeWriter("Resetting the level...Press [ENTER] to continue");
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
