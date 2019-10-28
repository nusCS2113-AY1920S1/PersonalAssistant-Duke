package Commands;

import Exceptions.FarmioFatalException;
import Farmio.*;
import Exceptions.FarmioException;

public class CommandMenu extends Command {

    /**
     * Shows the menu
     * @param farmio the game which stage is set as MENU_START
     * @throws FarmioFatalException if simulation file is missing
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Menu.show(farmio, true);
        farmio.setStage(Farmio.Stage.MENU);
    }
}
