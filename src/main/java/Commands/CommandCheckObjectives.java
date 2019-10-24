package Commands;

import Farmio.Farmio;
import Exceptions.FarmioException;
import Farmio.Level;
import Places.Farm;

import javax.swing.plaf.synth.SynthTabbedPaneUI;

public class CommandCheckObjectives extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        Level.objectiveResult answer = farmio.getLevel().checkAnswer(farmio);
        if (answer == Level.objectiveResult.NOT_DONE) {
            farmio.getUi().typeWriter("Continue tonext day? \n [ENTER] to continue [RESET] to reset to start of the level");
            farmio.setStage(Farmio.Stage.DAY_END);
        } else if (answer == Level.objectiveResult.DONE) {
            farmio.getUi().typeWriter("Well done! You have completed the level! Press [ENTER] to continue");
            farmio.setStage(Farmio.Stage.LEVEL_END);
        } else if (answer == Level.objectiveResult.FAILED) {
            //todo Implement Level.getFeedback() to print better feedback to user here
            farmio.getUi().typeWriter("Oh no! The objectives were not met by the deadline! Press [ENTER] to reset");
            farmio.setStage(Farmio.Stage.LEVEL_FAILED);
        } else if (answer == Level.objectiveResult.INVALID) {
            farmio.getUi().typeWriter("Oh no! There has been an error during code execution! Press [ENTER] to reset");
            farmio.setStage(Farmio.Stage.LEVEL_FAILED);
        }
    }
}
