package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Ui;

public class CommandGameNew extends Command {
    /**
     * Creates a new game
     * @param farmio The game to be reinitialised as a new game
     * @throws FarmioFatalException if simulation file is not found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        farmio.setFarmer(new Farmer());
        farmio.getSimulation().simulate("GameNew", 0 , true);
        ui.typeWriter("New Game Created! Starting tutorial...", true);
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}