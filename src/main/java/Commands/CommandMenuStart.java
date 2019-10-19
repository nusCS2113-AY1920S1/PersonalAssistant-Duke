package Commands;

import Exceptions.FarmioFatalException;
import Farmio.*;
import Exceptions.FarmioException;

public class CommandMenuStart extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        Menu.show(farmio.getUi(), farmio.getStorage(), false);
        farmio.setStage(Farmio.Stage.MENU_START);
    }
}
