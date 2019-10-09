//package duke.logic.command.recipe;
//
//import duke.commons.DukeException;
//import duke.entities.recipe.Recipe;
//import duke.logic.command.exceptions.CommandException;
//import duke.model.Model;
//import duke.storage.BakingList;
//
//import java.util.List;
//import java.util.Map;
//
//public class AddRecipeCommand extends RecipeCommand {
//
//    private Map<String, List<String>> params;
//    private Recipe recipe;
//
//    public AddRecipeCommand(Map<String, List<String>> params) {
//        this.params = params;
//    }
//
//    /**
//     * Execute the command.
//     *
//     * @param model A BakingList.
//     * @throws DukeException If the execution fails.
//     */
//
//    @Override
//    public void execute(Model model) throws CommandException {
//        recipe = getRecipe();
//        addRecipe(recipe, model);
//        storage.serialize(model);
//        ui.refreshRecipeListPage(model.getRecipeList());
//    }
//
//    private void addRecipe(Recipe recipe, BakingList bakingList) {
//        bakingList.getRecipeList().add(0, recipe);
//    }
//
//    private Recipe getRecipe() {
//
//        if (params.containsKey("name")) {
//            Recipe recipe = new Recipe(params.get("name").get(0));
//
//            if (params.containsKey("ingt")) {
//                //check if it is in the existing ingredient list.
//                for (int i = 0; i < params.get("ingt").size(); i++) {
//                    //recipe.addIngredient(new Ingredient(params.get("ingt").get(i)));
//                }
//            }
//
//            if (params.containsKey("step")) {
//                for (int i = 0; i < params.get("step").size(); i++) {
//                    //recipe.addStep(new Step(params.get("step").get(i)));
//                }
//            }
//
//            return recipe;
//        }
//        System.out.println("AddRecipeCommand.java function execute return null");
//        return null;
//    }
//
//
//}
