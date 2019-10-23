package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;

public class CommandTaskEdit extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        System.out.println("test\r");
        farmio.getUi().getInput();
        System.out.println("exiting");
    }
}
