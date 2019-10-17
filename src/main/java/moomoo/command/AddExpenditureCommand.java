package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.CategoryList;
import moomoo.task.Storage;
import moomoo.task.MooMooException;
import moomoo.task.Category;
import moomoo.task.Ui;
import moomoo.task.Expenditure;

public class AddExpenditureCommand extends Command {

    private String expenditureName;
    private double amount;

    /**
     * Command that takes in the amount of money spent and the expenditure name from the Parser as strings
     * to be converted.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input to be given by the user.
     * @param expenditureName Input to be given by the user.
     */
    public AddExpenditureCommand(boolean isExit, String input, String expenditureName) {
        super(isExit, input);
        this.expenditureName = expenditureName;
        this.amount = Double.parseDouble(input);
    }

    @Override
    public void execute(Budget budget, CategoryList categoryList, Category category, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(budget, categoryList, category, ui, storage);

        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).toString().equals(expenditureName)) {
                Expenditure newExpenditure = new Expenditure(amount);
                categoryList.get(i).add(newExpenditure);
                ui.showNewExpenditureMessage(expenditureName);
            } else {
                ui.showErrorMessage("Please add the category first.");
            }
        }
    }
}

