package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.AsciiColours;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class CommandDayStart extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        farmio.getSimulation().animate("DayStart", 1, 5);
        ui.show("Press ENTER to " + AsciiColours.MAGENTA + AsciiColours.UNDERLINE + "start the day!" + AsciiColours.SANE);
        farmio.setStage(Farmio.Stage.RUNNING_DAY);
    }
}
