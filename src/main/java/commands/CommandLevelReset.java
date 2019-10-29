package commands;

import exceptions.FarmioException;
import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Farmer;
import farmio.Storage;
import frontend.Ui;

public class CommandLevelReset extends Command {
    /**
     * Resets the level and farmer variables to previous state.
     * @param farmio The game which is reverted to a previous state.
     * @throws FarmioFatalException if simulation file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        try {
            farmio.setFarmer(new Farmer(storage.loadFarmer()));
        } catch (FarmioException e) {
            ui.showWarning(e.getMessage());
            ui.showInfo("Attempting recovery process.");
            try {
                farmio.setFarmer(new Farmer(storage.loadFarmerBackup()));
            } catch (FarmioException ex) {
                ui.showError("Recovery process failed!");
                ui.showInfo("Game cannot continue. Exiting now.");
            }
            ui.showInfo("Recovery successful.");
        }
        farmio.getUi().typeWriter("Level reset successful! Press [ENTER] to continue", false);
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
