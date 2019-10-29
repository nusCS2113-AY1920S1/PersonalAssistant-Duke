package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Menu;

public class CommandMenu extends Command {

    /**
     * Shows the menu.
     * @param farmio the game which stage is set as MENU.
     * @throws FarmioFatalException if simulation file is missing.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Menu.show(farmio, true);
        farmio.setStage(Farmio.Stage.MENU);
    }
}
