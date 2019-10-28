package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;

public class CommandLevelReset extends Command {
    /**
     * Resets the level and farmer variables to previous state
     * @param farmio The game which is reverted to a previous state
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        //TODO implement level.reset to reset the stage in case user types some wrong thing
        farmio.getUi().typeWriter("Resetting the level...Press [ENTER] to continue", false);
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
