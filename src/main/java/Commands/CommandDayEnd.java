package Commands;

import Farmio.Farmio;
import Exceptions.FarmioException;
import FrontEnd.Simulation;

public class CommandDayEnd extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        new Simulation("DayEnd" ,farmio).animate(0);
        farmio.getUi().show("Press ENTER to start your next day!");
        //farmer.nextLevel();
        farmio.setStage(Farmio.Stage.START_OF_DAY);
    }
}
