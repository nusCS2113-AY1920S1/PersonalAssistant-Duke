package owlmoney.logic.command.goals;

import owlmoney.logic.command.Command;
import owlmoney.model.goals.Goals;
import owlmoney.model.goals.exception.GoalsException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

import java.util.Date;

/**
 * Executes AddGoalsCommand to add a new goal object.
 */
public class AddGoalsCommand extends Command {

    private final String name;
    private final double amount;
    private final Date date;

    /**
     * Creates an instance of AddSavingCommand.
     *
     * @param name   Name of new goal object.
     * @param amount Income of new goal object.
     * @param date   Initial amount of new goal object.
     */
    public AddGoalsCommand(String name, double amount, Date date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Executes the function to create a new goals in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws GoalsException invalid parameters / attempt to add the same goal name.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws GoalsException {
        Goals newGoals = new Goals(this.name, this.amount, this.date);
        profile.addGoals(newGoals, ui);
        return this.isExit;
    }
}
