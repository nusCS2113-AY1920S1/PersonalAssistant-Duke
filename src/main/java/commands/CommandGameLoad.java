package commands;

import exceptions.FarmioFatalException;
import farmio.Farmer;
import farmio.Farmio;
import farmio.Storage;
import farmio.Level;
import frontend.Ui;
import exceptions.FarmioException;

public class CommandGameLoad extends Command {
    /**
     * Tries to Load the game and creates a new game if unsuccessful.
     * @param farmio the game to be resumed from load state.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        try {
            farmio.setFarmer(new Farmer(storage.loadFarmer()));
            Level level = new Level(storage.getLevel(farmio.getFarmer().getLevel()));
            farmio.setLevel(level);
            farmio.getSimulation().simulate("GameLoad", 0);
            ui.typeWriter("Load Game Success!", true);
        } catch (FarmioException e) {
            if (farmio.getStage() == Farmio.Stage.MENU_START) {
                farmio.getSimulation().simulate("GameNew", 0, true);
                ui.showWarning(e.getMessage());
                ui.typeWriter("Starting a new game.", true);
                farmio.setStage(Farmio.Stage.NAME_ADD);
                return;
            } else {
                ui.showWarning(e.getMessage());
                ui.showInfo("Load game failed! Resume to previous session.");
            }
        }
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
