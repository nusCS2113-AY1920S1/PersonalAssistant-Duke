package moomoo;

import java.util.ArrayList;

public class ListCategoryCommand extends Command{
    public ListCategoryCommand() {
        super();
    }

    @Override
    public void execute(Ui ui, CategoryList categories) {
        super.execute(ui, categories);
        categories.list(ui);
    }
}
