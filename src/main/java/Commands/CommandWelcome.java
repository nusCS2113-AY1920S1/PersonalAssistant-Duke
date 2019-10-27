package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Ui;

public class CommandWelcome extends Command {

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        farmio.getSimulation().simulate("Welcome", 1,true);
        ui.show( "Press ENTER to continue.");
    }
}
