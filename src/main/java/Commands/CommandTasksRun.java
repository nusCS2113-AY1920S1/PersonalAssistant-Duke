package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import FrontEnd.AsciiColours;

public class CommandTasksRun extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        farmio.getFarmer().startDay(farmio);
        farmio.getUi().show("\n\n" + AsciiColours.MAGENTA + AsciiColours.UNDERLINE +  "End of Day!" + AsciiColours.SANE + " Press ENTER to continue!");
        farmio.setStage(Farmio.Stage.CHECK_OBJECTIVES);
    }
}