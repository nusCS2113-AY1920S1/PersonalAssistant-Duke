package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class CommandDayStart extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        farmio.getUi().show("Press ENTER to start the day!");
        farmio.setStage(Farmio.Stage.RUNNING_DAY);
    }
}
