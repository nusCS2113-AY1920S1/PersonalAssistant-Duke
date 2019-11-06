package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Farmer;
import frontend.AsciiColours;

public class CommandDayEnd extends Command {
    /**
     * Sets and shows the end of day and sets the next day.
     * @param farmio the game where stage is changed to DAY_START.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        farmio.getSimulation().simulate("DayEnd", 0,4);
        farmio.getUi().show(AsciiColours.MAGENTA + AsciiColours.UNDERLINE +  "Day Ended" + AsciiColours.SANE);
        farmio.getUi().sleep(700);
        Farmer farmer = farmio.getFarmer();
        farmer.nextDay();
        farmio.getSimulation().simulate();
        farmio.getUi().sleep(700);
        farmio.setStage(Farmio.Stage.DAY_START);
    }
}
