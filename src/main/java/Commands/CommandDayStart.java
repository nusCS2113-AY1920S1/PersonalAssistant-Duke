package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import FrontEnd.Simulation;

public class CommandDayStart extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        new Simulation("DayStart" ,farmio).animate(1, 18);
        farmio.getUi().show("Press ENTER to start the day!");
        farmio.setStage(Farmio.Stage.RUNNING_DAY);
    }
}
