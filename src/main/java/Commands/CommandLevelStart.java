package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;

public class CommandLevelStart extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        //put the code to run the simulation at the start of the level here
        farmio.getUi().typeWriter("Narratives be here!");
        farmio.setStage(Farmio.Stage.TASK_ADD);
    }
}
