package duke.storage;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;

import java.io.IOException;

public class FridgeStorage extends Storage<Ingredient> {
    /**
     * The constructor method for Storage.
     *
     * @param fp used to specify the location of the file in the hard disc.
     */
    public FridgeStorage(String fp) {
        super(fp);
        entries = new IngredientsList();
    }

    @Override
    public GenericList<Ingredient> generate() throws DukeException {
        for (String next : contentSoFar) {
            //splitting each line to extract the task:
            //type - words[0], done or not - words[1], description - words[2], and more.
            String[] words = next.split("\\|");
            if (words.length != 3)
                throw new DukeException("Error while reading from the Fridge Storage words length is "+words.length+" adn is "+words[0]);
            entries.addEntry(new Ingredient(words[0], Integer.parseInt(words[1]), words[2]));
        }
        // System.out.println("in generate size of list is "+entries.size());
        return entries;
    }

    public void removeAllExpired() throws IOException, DukeException {

        for (int i = 0; i < entries.size(); i++)
            if (entries.getEntry(i).isExpired()) {
                entries.removeEntry(i);
            }
        update();
    }
}
