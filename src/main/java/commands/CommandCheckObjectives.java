package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Level;
import java.util.List;

public class CommandCheckObjectives extends Command {
    /**
     * Checks if objectives are met and sets the corresponding stage according to the results.
     * @param farmio the game where the stage is set based on the results.
     * @throws FarmioFatalException if simulaton file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Level.ObjectiveResult answer = farmio.getLevel().checkAnswer(farmio);
        List<String> feedback = farmio.getLevel().getFeedback(farmio, answer);
        for (String i : feedback) {
            farmio.getUi().typeWriter(i,false);
        }

        if (answer == Level.ObjectiveResult.NOT_DONE) {
            farmio.setStage(Farmio.Stage.DAY_END);
        } else if (answer == Level.ObjectiveResult.DONE) {
            farmio.setStage(Farmio.Stage.LEVEL_END);
        } else if (answer == Level.ObjectiveResult.FAILED) {
            farmio.setStage(Farmio.Stage.LEVEL_FAILED);
        } else if (answer == Level.ObjectiveResult.INVALID) {
            farmio.setStage(Farmio.Stage.LEVEL_FAILED);
        }
    }
}
