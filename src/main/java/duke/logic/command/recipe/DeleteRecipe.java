//package duke.logic.command.recipe;
//
//import duke.commons.DukeException;
//import duke.entities.recipe.Recipe;
//import duke.logic.command.commons.Command;
//import duke.logic.command.commons.Undoable;
//import duke.logic.command.exceptions.CommandException;
//import duke.model.Model;
//
//import java.util.List;
//import java.util.Map;
//
//public class DeleteRecipe extends Command implements Undoable {
//
//    private Recipe recipe;
//    private int index;
//    private Map<String, List<String>> params;
//
//    public DeleteRecipe(Map<String, List<String>> params) throws DukeException {
//        this.params = params;
//
//    }
//
//
//    /**
//     * Execute the command.
//     *
//     * @param model A BakingList.
//     * @throws DukeException If the execution fails.
//     */
//    @Override
//    public void execute(Model model) throws CommandException {
//        //this.recipe = getRecipe(bakingList.getRecipeList());
//        //bakingList.getRecipeList().remove(recipe);
//        storage.serialize(model);
//        //ui.refreshRecipeListPage(bakingList.getRecipeList());
//    }
//
//    private Recipe getRecipe(List<Recipe> recipes) {
//        if (params.containsKey("secondary")) {
//            String indexParams = params.get("secondary").get(0);
//            return recipes.get(Integer.parseInt(indexParams));
//        }
//        System.out.println("DeleteRecipeCommand function getRecipe return null");
//        return null;
//    }
//
//    @Override
//    public void undo(Model model) throws CommandException {
//
//    }
//
//    @Override
//    public void redo(Model model) throws CommandException {
//
//    }
//}
