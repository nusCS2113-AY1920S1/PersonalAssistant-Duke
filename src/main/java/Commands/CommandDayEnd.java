package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import FrontEnd.Simulate;

public class CommandDayEnd extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        new Simulate("DayEnd" ,farmio).showFrame(0);
        farmio.getUi().show("Press ENTER to start your next day!");
        //farmer.nextLevel();
        farmio.setStage(Farmio.Stage.START_OF_DAY);
    }
}
