package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.AsciiColours;
import frontend.Ui;

public class CommandDayStart extends Command {
    /**
     * Shows and sets the start of a new day.
     * @param farmio The game where its stage is set to RUNNING_DAY.
     * @throws FarmioFatalException if simulation file is missing.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        farmio.getSimulation().simulate("DayStart", 1, 5);
        ui.show(AsciiColours.MAGENTA + AsciiColours.UNDERLINE + "Day Started!" + AsciiColours.SANE);
        ui.sleep(300);
        farmio.setStage(Farmio.Stage.RUNNING_DAY);
    }
}