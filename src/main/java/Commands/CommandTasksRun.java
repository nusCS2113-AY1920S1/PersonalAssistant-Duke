package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;

public class CommandTasksRun extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        farmio.getFarmer().startDay(farmio);
        farmio.getUi().show("End of Day!");
        farmio.setStage(Farmio.Stage.CHECK_OBJECTIVES);
    }
}