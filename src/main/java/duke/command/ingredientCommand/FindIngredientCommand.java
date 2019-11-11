package duke.command.ingredientCommand;

import duke.command.Command;
import duke.list.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.list.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.text.SimpleDateFormat;

/**
 * Represents a specific {@link Command} used to find an ingredient in the {@link Fridge}.
 *
 * @@author x3chillax
 */
public class FindIngredientCommand extends Command {

    private String toFind;
    private SimpleDateFormat simpleDateFormat;

    /**
     * Constructor of the class {@link FindIngredientCommand}
     * Creates a new {@link FindIngredientCommand} to find the ingredient given its name
     *
     * @param toFind, name of the ingredient we want to find
     */
    public FindIngredientCommand(String toFind) {
        this.toFind = toFind;
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public void execute(Fridge ingList, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException {
        int i = 1;
        StringBuilder sb = new StringBuilder();

        for (Ingredient ingredient : ingList.getAllIngredients().getAllEntries()) {     //for every ingredient, scan through the fridge
            i += 1;
            if (ingredient.getName().equals(toFind)) {
                sb.append("\t ").append(i - 1).append(". ").append(ingredient.getName()).append(" ").append(ingredient.getAmount()).append(" ").append(simpleDateFormat.format(ingredient.getExpiryDate())).append(".");
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
