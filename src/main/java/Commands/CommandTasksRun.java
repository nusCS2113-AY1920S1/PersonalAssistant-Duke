package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class CommandTasksRun extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        farmio.getFarmer().startDay(farmio);
        farmio.getUi().show("End of Day!");
        farmio.setStage(Farmio.Stage.CHECK_OBJECTIVES);
    }
}
