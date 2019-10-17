package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import FrontEnd.Simulate;

public class CommandDayStart extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        new Simulate("DayStart" ,farmio).simulate(1, 20);
        farmio.getUi().show("Press ENTER to start the day!");
        farmio.setStage(Farmio.Stage.RUNNING_DAY);
    }
}
