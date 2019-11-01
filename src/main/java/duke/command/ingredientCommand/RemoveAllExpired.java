package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;

public class RemoveAllExpired extends Cmd<Ingredient> {

    private Fridge fridge;
    public RemoveAllExpired(Fridge fridge){
        this.fridge=fridge;
    }
    @Override
    public void execute(GenericList<Ingredient> genlist, Ui ui, Storage storage) throws DukeException, IOException {
        if(fridge.hasExpiredIngredients()) {
            //System.out.println(" has expired "+fridge.getExpiredIngredients().size());
            IngredientsList expired=fridge.removeExpired();
            (storage).update();
            ui.show(" Removed: "+expired.toString());
        }
        else
            throw new DukeException("Seems like you don't have any expired ingredients in the fridge!");
    }
}
