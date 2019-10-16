package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class CommandGameQuit extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        farmio.setExit();
    }
}
