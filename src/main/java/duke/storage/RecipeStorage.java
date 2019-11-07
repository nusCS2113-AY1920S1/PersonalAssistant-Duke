package duke.storage;

import duke.dish.Dish;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;

public class RecipeStorage extends Storage<Dish> {

    /**
     * The constructor method for RecipeStorage.
     *
     * @param fp used to specify the location of the file in the hard disc.
     * @@author CEGLincoln
     */
    public RecipeStorage(String fp) {
        super(fp);
    }

    @Override
    public GenericList<Dish> generate() throws DukeException {
        for (String next : contentSoFar) {
            String[] words = next.split("\\|");
            //module Parse
            String name = words[0];
            IngredientsList ilst = new IngredientsList();
            for (int i=0; i<words.length; i++) {
                String desc = "";
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
