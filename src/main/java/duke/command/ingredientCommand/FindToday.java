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
 * Represents a specific {@link Command} used to list Ingredients that have expired today
 * in the {@link Fridge}.
 *
 * @@author x3chillax
 */
public class FindToday extends Command {

    private SimpleDateFormat simpleDateFormat;

    /**
     * Constructor of the class {@link FindToday}
     * Creates a new {@link FindToday} to format the dates
     */
    public FindToday() {
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for (Ingredient ingredient : fridge.getAllIngredients().getAllEntries()) {     //for every ingredient, scan through the ingredient list
            i += 1;
            if (ingredient.isExpiredToday(simpleDateFormat.format(ingredient.getExpiryDate()))) {
                sb.append("\t ").append(i - 1).append(". ").append(fridge.getIngredient(ingredient).toStringNoWarning()).append(".");
                sb.append(System.lineSeparator());
            }
        }
        if (sb.length() == 0) {
            System.out.println("No expired ingredients for today!");
        } else {
            System.out.println("\t Here are the expired ingredients for today");
            ui.showTask(sb.toString());
        }
    }
}
