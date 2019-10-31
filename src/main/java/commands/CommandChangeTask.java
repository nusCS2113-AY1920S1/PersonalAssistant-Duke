package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Storage;

public abstract class CommandChangeTask extends Command {

    protected void saveTaskandResetScreen(Farmio farmio) throws FarmioFatalException {
        Storage storage = farmio.getStorage();
        storage.storeFarmer(farmio.getFarmer());
        farmio.getSimulation().simulate(farmio.getLevel().getPath(), farmio.getLevel().getNarratives().size() - 1);
    }
}
