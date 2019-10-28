package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.AsciiColours;

public class CommandTasksRun extends Command {
    /**
     * Run the tasklist and prepare to check if objectives are met
     * @param farmio the game which stage change will be changed
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        farmio.getFarmer().startDay(farmio);
        farmio.getUi().show(AsciiColours.MAGENTA + AsciiColours.UNDERLINE +  "Day Ended" + AsciiColours.SANE);
        farmio.getUi().sleep(1000);
        farmio.setStage(Farmio.Stage.CHECK_OBJECTIVES);
    }
}