package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Ui;

public class CommandGameNew extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        farmio.setFarmer(new Farmer());
        farmio.getSimulation().simulate("GameNew", 0 , true);
        ui.typeWriter("New Game Created! Starting tutorial...", true);
        ui.typeWriter("Enter your name:", false);
        farmio.setStage(Farmio.Stage.NAME_ADD);
    }
}