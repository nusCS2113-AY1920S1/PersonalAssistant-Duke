package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class CommandWelcome extends Command {

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Ui ui = farmio.getUi();
        new Simulation("Welcome", ui, farmio.getStorage()).animate(1);
        ui.show( "Press ENTER to continue.");
    }
}
