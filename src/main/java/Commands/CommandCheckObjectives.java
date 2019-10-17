package Commands;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class CommandCheckObjectives extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        if (farmio.getLevel().checkAnswer(farmio)) {
            //proceed to next level
            farmio.getUi().show("Press ENTER");
            farmio.setStage(Farmio.Stage.END_OF_DAY);
        } else {
            //output message, reload current level to try again
        }
    }
}
