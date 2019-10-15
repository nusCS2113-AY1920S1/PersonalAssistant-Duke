package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class CommandTasksRun extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        farmio.getFarmer().startDay(farmio);
    }
}
