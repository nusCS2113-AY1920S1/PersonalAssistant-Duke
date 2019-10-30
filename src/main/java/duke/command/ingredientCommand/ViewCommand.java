package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.Date;

/**
 * Represents a specific {@link Cmd} used to find a String occurring in the {@link TaskList}.
 * One of the B-Extensions.
 * @author x3chillax
 */
public class ViewCommand extends Cmd<Ingredient> {

    private Date toView;

    public ViewCommand(Date toView) {
        this.toView = toView;
    }

    @Override
    public void execute(GenericList<Ingredient> ingredientList, Ui ui, Storage storage) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        try {
            for (Ingredient ingredient : ingredientList.getAllEntries()) {
                if ((ingredient.getExpiryDate()).equals(toView)) {
                    //TODO: needs work on this part. comparing of time use Date always takes into account time 0000
                    sb.append("\t ").append(i++).append(".").append(ingredient.toString());
                    sb.append(System.lineSeparator());
                }
            }
            if (sb.length() == 0) {
                System.out.println("No matching date found! ");
            } else {
                System.out.println("\t Here are the tasks in the requested date:");
            }
            sb.setLength(sb.length() - 1); // to remove the last new line
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
