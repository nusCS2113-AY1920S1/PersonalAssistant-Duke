package moomoo.command.category;

import moomoo.command.Command;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Budget;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.Ui;
import moomoo.feature.storage.Storage;

import java.util.ArrayList;

public class ListCategoryCommand extends Command {
    public ListCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {

        ArrayList<String> categoryArray = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            categoryArray.add((i + 1) + ". "
                    + category.name() + " [ $"
                    + category.getTotal() + " ]");
        }
        Ui.showList(categoryArray, getLongestCategory(categoryArray));
    }

    private int getLongestCategory(ArrayList<String> categoryArray) {
        int longestEntry = 0;
        for (String entry : categoryArray) {
            if (entry.length() > longestEntry) {
                longestEntry = entry.length();
            }
        }
        return longestEntry;
    }
}
