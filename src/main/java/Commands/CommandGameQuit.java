package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import FrontEnd.Simulation;

public class CommandGameQuit extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        new Simulation("GameExit", farmio).animate(0);
        farmio.setExit();
    }
}
