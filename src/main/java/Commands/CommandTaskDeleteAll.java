package Commands;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;

public class CommandTaskDeleteAll extends Command {

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        farmio.getFarmer().getTasks().clear();
        farmio.getUi().showInfo("You have deleted all tasks!");
        farmio.getSimulation().animate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size());
    }
}
