package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;

public class CommandGameSave extends Command{

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        if(farmio.getStorage().storeFarmer(farmio.getFarmer())){
            ui.show("Game saved successfully!");
        } else {
            ui.show("Game save failed!! Try again later.");
        }
    }
}
