package commands;

import farmio.Farmio;
import farmio.Storage;

public abstract class CommandChangeTask extends Command {

    protected void saveTask(Farmio farmio) {
        Storage storage = farmio.getStorage();
        storage.storeFarmer(farmio.getFarmer());
    }
}
