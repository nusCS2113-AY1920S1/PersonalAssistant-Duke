package duke.command.ingredientCommand;

import duke.command.Command;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;

public class UseCommand extends Command<Ingredient> {
    private Ingredient toUse;

    public UseCommand(Ingredient ingredient){
        toUse = ingredient;
    }

    @Override
    public void execute(GenericList<Ingredient> ingredientList, Ui ui, Storage storage) throws DukeException, IOException {
        if(ingredientList.removeEntry(toUse)){
        ui.show("Great you used "+ toUse.toStringWithoutDate());
        storage.update();
    }else
        ui.show("There is not a sufficient amount of "+toUse.getName()+" that is not expired, maybe you could buy some first? ");
    }
}
