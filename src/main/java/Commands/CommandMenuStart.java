package Commands;

import Farmio.*;
import Exceptions.FarmioException;

public class CommandMenuStart extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        Menu.show(farmio.getUi(), farmio.getStorage(), false);
        farmio.setStage(Farmio.Stage.MENU_START);
    }
}
