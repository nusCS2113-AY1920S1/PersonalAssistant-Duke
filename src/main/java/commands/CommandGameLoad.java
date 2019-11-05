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
            Farmer farmer = new Farmer(storage.loadFarmer());
            farmio.setFarmer(farmer);
            Level level = new Level(storage.getLevel(farmer.getLevel()), farmer.getName());
            farmio.setLevel(level);
            farmio.getSimulation().simulate("GameLoad", 0, 8, true);
            farmio.getSimulation().simulate("GameLoad", 9);
            ui.typeWriter("Load Game Successful!, press [ENTER] to continue or enter [skip] to skip the story..", false);
        } catch (FarmioException e) {
            if (farmio.getStage() == Farmio.Stage.MENU_START || farmio.getStage() == Farmio.Stage.WELCOME) {
                farmio.getSimulation().simulate("GameNew", 0, true);
                ui.showWarning(e.getMessage() + ". Starting a new game.");
                ui.typeWriter("New Game Created!", false);
                ui.typeWriter("Enter your name:", false);
                farmio.setStage(Farmio.Stage.NAME_ADD);
                return;
            } else {
                ui.showWarning(e.getMessage());
                ui.typeWriter("Load game failed! Resume to previous session.", true);
            }
        }
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
