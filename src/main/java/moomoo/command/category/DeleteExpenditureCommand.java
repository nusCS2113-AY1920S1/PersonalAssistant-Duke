package moomoo.command.category;


import moomoo.command.Command;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.category.Expenditure;
import moomoo.feature.storage.ExpenditureStorage;
import moomoo.feature.storage.Storage;

public class DeleteExpenditureCommand extends Command {

    private String categoryName;
    private int expenditureIndex;

    /**
     * Command that takes in the amount of money spent and the expenditure name and date under which category from the
     * Parser as strings to be converted.
     * @param expenditureIndex Name of the expenditure spent on. (e.g. Chicken Rice)
     * @param categoryName    Category that the expenditure is being spent on. (e.g. Food)
     */
    public DeleteExpenditureCommand(int expenditureIndex, String categoryName) {
        super(false, "");
        this.categoryName = categoryName;
        this.expenditureIndex = expenditureIndex;
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {

        Category cat = categoryList.get(categoryName);
        if (cat == null) {
            throw new MooMooException("Sorry I could not find a category named " + categoryName);
        }
        try {
            Expenditure expenditure = cat.get(expenditureIndex);
            String name = expenditure.getName();
            ExpenditureStorage.deleteFromFile(expenditure.toString());
            categoryList.get(categoryName).delete(expenditureIndex);
            Ui.setOutput("Expenditure named " + name + " re-MOOO-ved\n"
                + "From category: " + categoryName);
        } catch (IndexOutOfBoundsException e) {
            throw new MooMooException("Sorry I don't see an expenditure with that name.");
        }
    }
}
