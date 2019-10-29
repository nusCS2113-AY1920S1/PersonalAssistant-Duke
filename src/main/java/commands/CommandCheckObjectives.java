package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import farmio.Level;

public class CommandCheckObjectives extends Command {
    /**
     * Checks if objectives are met and sets the corresponding stage according to the results.
     * @param farmio the game where the stage is set based on the results.
     * @throws FarmioFatalException if simulaton file cannot be found.
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Level.objectiveResult answer = farmio.getLevel().checkAnswer(farmio);
        farmio.getSimulation().simulate();
        farmio.getUi().typeWriter(farmio.getLevel().getFeedback(farmio), false); // feedbacks
        if (answer == Level.objectiveResult.NOT_DONE) {
            farmio.setStage(Farmio.Stage.DAY_END);
        } else if (answer == Level.objectiveResult.DONE) {
            farmio.setStage(Farmio.Stage.LEVEL_END);
        } else if (answer == Level.objectiveResult.FAILED) {
            farmio.setStage(Farmio.Stage.LEVEL_FAILED);
        } else if (answer == Level.objectiveResult.INVALID) {
            farmio.setStage(Farmio.Stage.LEVEL_FAILED);
        }
    }
}
