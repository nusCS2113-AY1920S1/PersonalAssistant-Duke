package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddCommand extends Cmd<Ingredient>{

    private Ingredient ingredient;

    public AddCommand(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void execute(GenericList<Ingredient> IngredientsList, Ui ui, Storage storage) throws DukeException {
        boolean flag = true;
        try {
            if(IngredientsList.size() == 0) { //if the list is empty, immediately add ingredient in it
                IngredientsList.addEntry(ingredient);
                ui.showAddedIngredient(ingredient.getName());
                storage.update();
            }
            else {
                for( int i = 0; i < IngredientsList.size(); i++) { //check for duplicates in list
                    if(IngredientsList.getEntry(i).getName().equals(ingredient.getName())){
                        flag = false; //ingredient already exist in list
                        break;
                    }
                }
                if(flag) { //if there are no duplicates
                    IngredientsList.addEntry(ingredient); // add ingredient into list
                    ui.showAddedDishes(ingredient.getName());
                    storage.update();
                }
                else { //if there are duplicates
                    System.out.println("\t ingredient already exist in list");
                }
            }
        } catch (Exception e) {
            throw new DukeException("unable to add ingredient");
        }
    }

}