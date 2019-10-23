package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Level;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class CommandGameNew extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        farmio.setFarmer(new Farmer());
        farmio.getSimulation().animate("GameNew", 0 , true);
        ui.typeWriter("New Game Created! Starting tutorial...");
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}