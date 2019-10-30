package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Farmer;
import farmio.Storage;
import frontend.AsciiColours;
import frontend.Ui;

public class CommandDayEnd extends Command {
    /**
     * Sets and shows the end of day and sets the next day.
     * @param farmio the game where stage is changed to DAY_START.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        Farmer farmer = farmio.getFarmer();
        farmio.getSimulation().simulate("DayEnd", 0,4);
        farmio.getUi().show(AsciiColours.MAGENTA + AsciiColours.UNDERLINE +  "Day Ended" + AsciiColours.SANE);
        farmio.getUi().sleep(700);
        farmer.nextDay();
        farmio.getSimulation().simulate();
        farmio.getUi().sleep(700);
        farmio.setStage(Farmio.Stage.DAY_START);
    }
}
