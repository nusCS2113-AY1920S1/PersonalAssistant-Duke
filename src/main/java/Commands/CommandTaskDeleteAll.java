package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;

public class CommandTaskDeleteAll extends Command {

    /**
     * Delete all tasks in the tasklist
     * @param farmio the game that contains the tasklist to be cleared
     * @throws FarmioFatalException if the simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        farmio.getFarmer().getTasks().clear();
        farmio.getSimulation().simulate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size());
        farmio.getUi().showInfo("You have deleted all tasks!");
    }
}
