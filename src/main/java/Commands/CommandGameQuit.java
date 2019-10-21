package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import FrontEnd.Simulation;

public class CommandGameQuit extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        farmio.getSimulation().animate("GameExit", 0, true);
        farmio.setExit();
    }
}
