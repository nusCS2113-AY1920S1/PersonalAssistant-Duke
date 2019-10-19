package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import FrontEnd.Simulation;

public class CommandGameQuit extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Simulation.animate(farmio.getUi(), farmio.getStorage(), farmio.getFarmer(), "GameExit", 0);
        farmio.setExit();
    }
}
