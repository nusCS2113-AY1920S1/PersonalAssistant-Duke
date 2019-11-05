package moomoo.command;

import moomoo.feature.ScheduleList;
import moomoo.feature.Budget;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.storage.Storage;
import moomoo.feature.Ui;


public class TotalCommand extends Command {

    public TotalCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Ui ui, Storage storage) {
        list(ui, categoryList);
        ui.showEnterCategoryMessage();
        int cat = ui.readNumber() - 1;
        ui.showEnterMonthMessage();
        int month = ui.readNumber();
        double monthlyTotal = categoryList.get(cat).getTotal(month);
        ui.showMonthlyTotal(monthlyTotal, categoryList.get(cat), month);
    }

    /**
     * Prints the current list of categories.
     * @param ui MooMoo's ui
     * @param categoryList list
     */
    public void list(Ui ui, CategoryList categoryList) {
        String categoryString = "";
        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            categoryString = categoryString.concat("\n" + (i + 1) + ". "
                    + category.name() + " [ $"
                    + category.getTotal() + " ]");
        }
        showCategoryList(categoryString, ui);
    }

    /**
     * Prints the list of categories.
     * @param categories list of current categories
     * @param ui MooMoo's ui
     */
    private void showCategoryList(String categories, Ui ui) {
        if (categories.isBlank()) {
            ui.setOutput("There are no existing categories now, add some using the (category add) command.");
        } else {
            ui.setOutput("These are your current categories:"
                    + "\n_______________________________________________"
                    + categories
                    + "\n_______________________________________________");
        }
    }
}
