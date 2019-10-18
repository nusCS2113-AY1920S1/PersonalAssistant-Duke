package Commands;

import Farmio.Farmio;
import Exceptions.FarmioException;
import FrontEnd.Simulation;

public class CommandGameQuit extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        new Simulation("GameExit", farmio).animate(0);
        isExit = true;
    }
}
