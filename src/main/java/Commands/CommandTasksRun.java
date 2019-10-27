package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import FrontEnd.AsciiColours;

public class CommandTasksRun extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        farmio.getFarmer().startDay(farmio);
        farmio.getUi().show(AsciiColours.MAGENTA + AsciiColours.UNDERLINE +  "Day Ended" + AsciiColours.SANE);
        farmio.getUi().sleep(1000);
        farmio.setStage(Farmio.Stage.CHECK_OBJECTIVES);
    }
}