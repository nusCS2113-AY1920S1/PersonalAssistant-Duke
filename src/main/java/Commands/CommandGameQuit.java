package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import FrontEnd.Simulate;

public class CommandGameQuit extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        new Simulate("GameExit", farmio).showFrame(0);
        isExit = true;
    }
}
