package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class CommandCheckObjectives extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        farmio.getUi().show(farmio.getLevel().checkAnswer());
    }
}
