package duke.storage;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.task.Todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FridgeStorage extends Storage<Ingredient> {
    /**
     * The constructor method for Storage.
     *
     * @param fp used to specify the location of the file in the hard disc.
     */
    public FridgeStorage(String fp) {
        super(fp);
    }

    @Override
    List<Ingredient> generate() throws DukeException {
        for (String next : contentSoFar) {
            //splitting each line to extract the task:
            //type - words[0], done or not - words[1], description - words[2], and more.
            String[] words = next.split("\\|");
           if(words.length!=3)
               throw new DukeException("Error while reading from the Fridge Storage");
           entries.add(new Ingredient(words[0],Integer.parseInt(words[1]),words[2]));
        }
        return entries;
    }

    @Override
    public void changeContent(int ingNb) throws DukeException {
        if (ingNb < 0) {
            throw new DukeException("The ingredient number should be positive, ingredient number entered was: " + ingNb);
        }
        try {
            contentSoFar = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            contentSoFar.set(ingNb, entries.get(ingNb).printInFile()); // changing the file content
            Files.write(path, contentSoFar, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DukeException("Error while updating the fridge file");
        }    }

    @Override
    public void addInFile(String task) throws IOException {

    }
}
