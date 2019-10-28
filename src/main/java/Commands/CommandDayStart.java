package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.AsciiColours;
import FrontEnd.Ui;

public class CommandDayStart extends Command {
    /**
     * Shows and sets the start of a new day
     * @param farmio The game where its stage is set to RUNNING_DAY
     * @throws FarmioFatalException if simulation file is missing
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        farmio.getSimulation().simulate("DayStart", 1, 5);
        ui.show(AsciiColours.MAGENTA + AsciiColours.UNDERLINE + "Day Started!" + AsciiColours.SANE);
        ui.sleep(400);
        farmio.setStage(Farmio.Stage.RUNNING_DAY);
    }
}