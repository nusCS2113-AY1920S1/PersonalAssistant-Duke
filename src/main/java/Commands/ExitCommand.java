package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class ExitCommand extends Command {

    @Override
    public void execute(Farmio farmio) throws FarmioException {
        isExit = true;
    }
}
