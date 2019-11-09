package duke.storage;

import duke.dish.Dish;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.order.Order;

import java.util.Date;
import java.util.List;

//@@author CEGLincoln
public class RecipeStorage extends Storage<Dish> {

    /**
     * The constructor method for RecipeStorage.
     *
     * @param fp used to specify the location of the file in the hard disc.
     * @@author CEGLincoln
     */
    public RecipeStorage(String fp) throws DukeException {
        super(fp);
        entries = new DishList();
    }

    @Override
    public GenericList<Dish> generate() throws DukeException {
        for (String next : contentSoFar) {
            String[] words = next.split("\\|");
            //module Parse
            String name = words[0];
            IngredientsList ilst = new IngredientsList();
            String desc = "";
            int amnt = 0;
            for (int i=1; i<words.length; i++) {
                if (i%2 == 1) {
                    //If i is odd, it would be the name of the ingredient
                    desc = words[i];
                }
                else if (i>1) {
                    //If i is even, it would be the amount of said ingredient
                    amnt = Integer.parseInt(words[i]);
                    //Now that we have both the desc and amnt
                    //default date i just put random expiry date
                    Ingredient ingt = new Ingredient(desc, amnt, "1/1/2100");
                    //So now we have an ingredient
                    ilst.addEntry(ingt);
                }
            }
            //endmodule
            entries.addEntry(new Dish(name, ilst));
        }
        return entries;
    }
}
