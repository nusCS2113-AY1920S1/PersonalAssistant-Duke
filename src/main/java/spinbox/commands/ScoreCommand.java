package spinbox.commands;

import spinbox.Ui;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.GradeList;
import spinbox.entities.Module;
import spinbox.entities.items.GradedComponent;
import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;
import java.util.HashMap;

public class ScoreCommand extends Command {
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";
    private static final String INVALID_FORMAT = "Please use the valid score format:\n"
            + "Absolute percentage: [score <moduleCode> / <index> marks:<attained>%]" + "\n"
            + "Relative percentage: [score <moduleCode> / <index> marks: <attained>/<maximum>";
    private static final String INVALID_VALUE = "PLease enter valid numerical value(s).";
    private static final String PROVIDE_INDEX = "Please provide a valid index of the graded component to be scored.";
    private static final String COMPONENT_SCORED = "The following component has been scored: ";

    private String moduleCode;
    private String content;

    /**
     * Constructor for initialization of variables to support scoring of graded components.
     * @param pageDataComponents page data components to provide context based input completion.
     * @param content A string containing the content of the processed user input.
     */
    public ScoreCommand(String[] pageDataComponents, String content) {
        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
        }
        this.content = content;
    }


    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode)
            throws SpinBoxException {
        checkIfOnModulePage(moduleCode);
        if (moduleContainer.checkModuleExists(moduleCode)) {
            try {
                HashMap<String, Module> modules = moduleContainer.getModules();
                Module module = modules.get(moduleCode);
                GradeList gradeList = module.getGrades();

                String[] scoreComponents = this.content.split(" marks:");
                int index = Integer.parseInt(content.split(" ")[0]) - 1;
                String[] scores = scoreComponents[1].split("/");

                if (scoreComponents[1].contains("%")) {
                    scoreComponents[1] = scoreComponents[1].replace("%", "");
                    double attainedPercentage = Double.parseDouble(scoreComponents[1]);
                    gradeList.updateGradeWeightedScore(index, attainedPercentage);
                } else if (scores.length == 2) {
                    double attainedScore = Double.parseDouble(scores[0]);
                    double maximumScore = Double.parseDouble(scores[1]);
                    gradeList.updateGradeWeightedScore(index, attainedScore, maximumScore);
                } else {
                    throw new InputException(INVALID_FORMAT);
                }
                return HORIZONTAL_LINE + "\n" + COMPONENT_SCORED + "\n"
                        + gradeList.get(index).toString() + "\n" + HORIZONTAL_LINE;
            } catch (NumberFormatException e) {
                throw new InputException(INVALID_VALUE);
            } catch (IndexOutOfBoundsException e) {
                throw new InputException(INVALID_FORMAT);
            }
        } else {
            return NON_EXISTENT_MODULE;
        }
    }
}
