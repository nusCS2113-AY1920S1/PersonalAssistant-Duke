package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;

public class CommandTaskEdit extends Command {
    /**
     * Edit a Task in the tasklist
     * @param farmio the game which contains the tasklist to be editted
     * @throws FarmioFatalException if the simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        System.out.println("test\r");
        farmio.getUi().getInput();
        System.out.println("exiting");
    }
}
