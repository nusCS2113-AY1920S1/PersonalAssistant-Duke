package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import Farmio.Level;

public class CommandCheckObjectives extends Command {
    /**
     * Checks if objectives are met and sets the corresponding stage according to the results
     * @param farmio the game where the stage is set based on the results
     * @throws FarmioFatalException if simulaton file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Level.objectiveResult answer = farmio.getLevel().checkAnswer(farmio);
        farmio.getSimulation().simulate();
        if (answer == Level.objectiveResult.NOT_DONE) {
            farmio.setStage(Farmio.Stage.DAY_END);
        } else if (answer == Level.objectiveResult.DONE) {
            farmio.getUi().typeWriter("Well done! You have completed the level!", false);
            farmio.setStage(Farmio.Stage.LEVEL_END);
        } else if (answer == Level.objectiveResult.FAILED) {
            //todo Implement Level.getFeedback() to print better feedback to user here
            farmio.getUi().typeWriter("Oh no! The objectives were not met by the deadline!", false);
            farmio.setStage(Farmio.Stage.LEVEL_FAILED);
        } else if (answer == Level.objectiveResult.INVALID) {
            farmio.getUi().typeWriter("Oh no! There has been an error during code execution!", false);
            farmio.setStage(Farmio.Stage.LEVEL_FAILED);
        }
    }
}
