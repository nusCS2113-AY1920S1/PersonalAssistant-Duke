package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Level;
import frontend.Ui;

public class CommandTasksHint extends Command {

    /**
     * Print hint or instructions for current level.
     * @param farmio the game which level is used to determine hint.
     * @throws FarmioFatalException if simulation file is not found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Level level = farmio.getLevel();
        farmio.getSimulation().simulate(level.getPath(),level.getNarratives().size() - 1);
        ui.showHint(farmio.getLevel().getHint());
    }
}