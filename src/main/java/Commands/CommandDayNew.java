package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class CommandDayNew extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        farmio.getUi().show("Press ENTER to start the day!");
        farmio.getUi().showNarrative(farmio.getLevel().getNarratives(), "Level1-01", farmio);
    }
}
