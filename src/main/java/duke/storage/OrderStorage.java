package duke.storage;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.list.GenericList;
import duke.order.Order;

public class OrderStorage extends Storage<Order> {
    /**
     * The constructor method for Storage.
     *
     * @param fp used to specify the location of the file in the hard disc.
     */
    public OrderStorage(String fp) {
        super(fp);
    }

    @Override
    GenericList<Order> generate() throws DukeException {
        for (String next : contentSoFar) {
            //splitting each line to extract the task:
            //type - words[0], done or not - words[1], description - words[2], and more.
            String[] words = next.split("\\|");
            if(words.length!=3)
                throw new DukeException("Error while reading from the order Storage");
            entries.addEntry(new Order());
        }
        return entries;
    }
}
