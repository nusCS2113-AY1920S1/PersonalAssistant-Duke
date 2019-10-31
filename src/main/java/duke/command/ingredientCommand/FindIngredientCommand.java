package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.text.SimpleDateFormat;

/**
 * Represents a specific {@link Cmd} used to find a String occurring in the {@link Ingredient}.
 */
public class FindIngredientCommand extends Cmd<Ingredient> {

    private String toFind;
    private String pattern = "dd/MM/yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public FindIngredientCommand(String toFind) {
        this.toFind = toFind;
    }

    @Override
    public void execute(GenericList<Ingredient> ingList, Ui ui, Storage storage) throws DukeException {
        int i = 1;
        StringBuilder sb = new StringBuilder();

        for (Ingredient ingredient : ingList.getAllEntries()) {     //for every ingredient, scan through the ingredientslist
            i += 1;
            if (ingredient.getName().equals(toFind))
            {
                sb.append("\t ").append(i-1).append(". ").append(ingredient.getName()).append(" ").append(ingredient.getAmount()).append(" ").append(simpleDateFormat.format(ingredient.getExpiryDate())).append(".");
                sb.append(System.lineSeparator());
            }
        }
        if (sb.length() == 0) {
            System.out.println("No such ingredient found!");
        } else {
            System.out.println("\t These are the ingredients you searched for!");
            ui.showTask(sb.toString());
        }
    }
}
