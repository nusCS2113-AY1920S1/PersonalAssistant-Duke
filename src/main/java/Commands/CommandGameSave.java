package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;

public class CommandGameSave extends Command{
    /**
     * Saves the game
     * @param farmio the game to be saved
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        if(farmio.getStorage().storeFarmerPartial(farmio.getFarmer())){
            farmio.getSimulation().simulate();
            ui.show("Game saved successfully!");
        } else {
            farmio.getSimulation().simulate();
            ui.show("Game save failed!! Try again later.");
        }
    }
}
