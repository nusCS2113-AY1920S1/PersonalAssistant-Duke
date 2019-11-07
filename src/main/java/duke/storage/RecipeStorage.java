package duke.storage;

import duke.dish.Dish;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.order.Order;

import java.util.List;

public class RecipeStorage extends Storage<Dish> {

    /**
     * The constructor method for RecipeStorage.
     *
     * @param fp used to specify the location of the file in the hard disc.
     * @@author CEGLincoln
     */
    public RecipeStorage(String fp) throws DukeException {
        super(fp);
    }

    @Override
    public GenericList<Dish> generate() throws DukeException {
        for (String next : contentSoFar) {
            String[] words = next.split("\\|");
            //module Parse
            String name = words[0];
            IngredientsList ilst = new IngredientsList();
            String desc = "";
            for (int i=0; i<words.length; i++) {
                int amnt;
                if (i%2 == 1) {
                    //If i is odd, it would be the name of the ingredient
                    desc = words[i];
                }
                else if (i>1) {
                    //If i is even, it would be the amount of said ingredient
                    amnt = Integer.parseInt(words[i]);
                    //Now that we have both the desc and amnt
                    Ingredient ingt = new Ingredient(desc, amnt, "");
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
