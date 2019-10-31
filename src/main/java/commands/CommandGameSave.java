package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import frontend.Ui;

public class CommandGameSave extends Command {
    /**
     * Saves the game.
     * @param farmio the game to be saved.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        String location = farmio.getStorage().storeFarmerPartial(farmio.getFarmer());
        if (location != null) {
            farmio.getSimulation().simulate();
            ui.show("Game saved successfully!\nGame save location:\n" + location);
        } else {
            farmio.getSimulation().simulate();
            ui.show("Game save failed!! Try again later.");
        }
    }
}
