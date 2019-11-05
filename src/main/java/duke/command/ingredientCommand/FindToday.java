package duke.command.ingredientCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.ui.Ui;

import java.text.SimpleDateFormat;

public class FindToday extends Command {

    private SimpleDateFormat simpleDateFormat;

    public FindToday(){
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os) {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for (Ingredient ingredient : fridge.getAllIngredients().getAllEntries())
        {     //for every ingredient, scan through the ingredient list
            i += 1;
            if (ingredient.isExpiredToday(simpleDateFormat.format(ingredient.getExpiryDate())))
            {
                sb.append("\t ").append(i-1).append(". ").append(fridge.getIngredient(ingredient).toStringNoWarning()).append(".");
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

