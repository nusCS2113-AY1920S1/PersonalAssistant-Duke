package moomoo.command;

import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Budget;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.storage.Storage;


public class TotalCommand extends Command {

    public TotalCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {
        list(categoryList);
        Ui.showEnterCategoryMessage();
        int cat = Ui.readNumber() - 1;
        Ui.showEnterMonthMessage();
        int month = Ui.readNumber();
        double monthlyTotal = categoryList.get(cat).getTotal(month);
        Ui.showMonthlyTotal(monthlyTotal, categoryList.get(cat), month);
    }

    /**
     * Prints the current list of categories.
     * @param categoryList list
     */
    public void list(CategoryList categoryList) throws MooMooException {
        String categoryString = "";
        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            categoryString = categoryString.concat("\n" + (i + 1) + ". "
                    + category.name() + " [ $"
                    + category.getTotal() + " ]");
        }
        showCategoryList(categoryString);
    }

    /**
     * Prints the list of categories.
     * @param categories list of current categories
     *
     */
    private void showCategoryList(String categories) {
        if (categories.isBlank()) {
            Ui.setOutput("There are no existing categories now, add some using the (category add) command.");
        } else {
            Ui.setOutput("These are your current categories:"
                    + "\n_______________________________________________"
                    + categories
                    + "\n_______________________________________________");
        }
    }
}
