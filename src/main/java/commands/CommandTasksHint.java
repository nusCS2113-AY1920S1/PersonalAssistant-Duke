package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Ui;

public class CommandTasksHint extends Command {
    private String message;

    public CommandTasksHint() {
        message = "Enter [Start] when you are ready to complete the objective";
    }

    /**
     * Print hint or instructions for current level.
     * @param farmio the game which level is used to determine hint.
     * @throws FarmioFatalException if simulation file is not found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        farmio.getSimulation().simulate();
        ui.typeWriter(farmio.getLevel().getHint(), false);
        ui.typeWriter(message, false);
    }
}