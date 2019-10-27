package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.AsciiColours;
import FrontEnd.Ui;

public class CommandDayStart extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        farmio.getSimulation().simulate("DayStart", 1, 5);
        ui.show(AsciiColours.MAGENTA + AsciiColours.UNDERLINE + "Day Started!" + AsciiColours.SANE);
        ui.sleep(400);
        farmio.setStage(Farmio.Stage.RUNNING_DAY);
    }
}