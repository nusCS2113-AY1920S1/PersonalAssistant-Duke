package moomoo;

public class AddCategoryCommand extends Command {

    private String categoryName;

    public AddCategoryCommand(String categoryName) {
        super();
        this.categoryName = categoryName;
    }

    @Override
    public void execute(Ui ui, CategoryList categories) {
        super.execute(ui, categories);
        Category newCategory = new Category(categoryName);
        categories.add(newCategory);
        ui.showNewCategoryMessage(categoryName);
    }
}
