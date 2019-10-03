package duke.command.recipe;

import duke.commons.DukeException;
import duke.entities.recipe.Recipe;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

public class AddRecipeCommand extends RecipeCommand {

    private Map<String, List<String>> params;
    private Recipe recipe;

    public AddRecipeCommand(Map<String, List<String>> params) {
        this.params = params;
    }

    /**
     * Execute the command.
     *
     * @param bakingList A BakingList.
     * @param storage    A Storage object which specifies the location of the data.
     * @param ui         A Ui object capable of controlling GUI.
     * @throws DukeException If the execution fails.
     */

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        recipe = getRecipe();
        addRecipe(recipe, bakingList);
        storage.serialize(bakingList);
        ui.refreshRecipeListPage(bakingList.getRecipeList());
    }

    private void addRecipe(Recipe recipe, BakingList bakingList) {
        bakingList.getRecipeList().add(0, recipe);
    }

    private Recipe getRecipe() {

        if (params.containsKey("name")) {
            Recipe recipe = new Recipe(params.get("name").get(0));

            if (params.containsKey("ingt")) {
                //check if it is in the existing ingredient list.
                for (int i = 0; i < params.get("ingt").size(); i++) {
                    //recipe.addIngredient(new Ingredient(params.get("ingt").get(i)));
                }
            }

            if (params.containsKey("step")) {
                for (int i = 0; i < params.get("step").size(); i++) {
                    //recipe.addStep(new Step(params.get("step").get(i)));
                }
            }

            return recipe;
        }
        System.out.println("AddRecipeCommand.java function execute return null");
        return null;
    }


}
