package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class CommandDayEnd extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        farmio.getUi().show("Press ENTER to start your next day!");
        //farmer.nextLevel();
        farmio.setStage(Farmio.Stage.START_OF_DAY);
    }
}
