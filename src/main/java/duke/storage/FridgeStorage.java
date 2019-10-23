package duke.storage;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;

import java.util.List;

public class FridgeStorage extends Storage<Ingredient> {
    /**
     * The constructor method for Storage.
     *
     * @param fp used to specify the location of the file in the hard disc.
     */
    public FridgeStorage(String fp) {
        super(fp);
        entries= new IngredientsList();
    }

    @Override
    GenericList<Ingredient> generate() throws DukeException {
        for (String next : contentSoFar) {
            //splitting each line to extract the task:
            //type - words[0], done or not - words[1], description - words[2], and more.
            String[] words = next.split("\\|");
           if(words.length!=3)
               throw new DukeException("Error while reading from the Fridge Storage");
           entries.addEntry(new Ingredient(words[0],Integer.parseInt(words[1]),words[2]));
        }
        return entries;
    }


}
