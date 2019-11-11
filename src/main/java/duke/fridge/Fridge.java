package duke.fridge;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.list.IngredientsList;
import duke.storage.FridgeStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a Fridge, used to store {@link Ingredient}s and allow changes to be made to them
 * @author Sara Djambazovska
 */
public class Fridge {
    private IngredientsList currentIngredients;

    /**
     * Constructor of the class {@link Fridge}
     * Creates a new {@link Fridge} from the Ingredients stored in the {@link FridgeStorage}  passed as a parameter,
     *
     * @param fridgeStorage the {@link FridgeStorage} containing the ingredients to initialize the Fridge with
     */
    public Fridge(FridgeStorage fridgeStorage) throws DukeException {
        currentIngredients = new IngredientsList(fridgeStorage.load().getAllEntries());
    }

    /**
     * Constructor of the class {@link Fridge}
     * Creates a new {@link Fridge} from the Ingredients stored in the {@link IngredientsList}  passed as a parameter,
     *
     * @param list the {@link IngredientsList} containing the ingredients to initialize the Fridge with
     */
    public Fridge(IngredientsList list) {
        this.currentIngredients = list;
    }

    /**
     * Empty Constructor of the class {@link Fridge}
     * Creates a new {@link Fridge} having an empty {@link IngredientsList}
     */
    public Fridge() {
        currentIngredients = new IngredientsList();
    }

    /**
     * Returns true if the {@link Fridge } has no ingredients stored
     *
     * @return true if the {@link IngredientsList} of the fridge is empty
     */
    public boolean isEmpty() {
        return currentIngredients.isEmpty();
    }

    /**
     * Returns an {@link IngredientsList} with all of the {@link Ingredient}s stored in the {@link Fridge}
     *
     * @return IngredientsList currently stored in the {@link Fridge}
     */
    public IngredientsList getAllIngredients() {
        return currentIngredients;
    }

    /**
     * Returns true if the {@link Fridge } has a sufficient amount of the Ingredient passed as an argument
     *
     * @param ingredient giving the amount and ingredient name to be checked if available
     * @return true if the needed amount is available in the {@link Fridge}, false otherwise
     */
    public boolean hasEnough(Ingredient ingredient) {
        return currentIngredients.hasEnough(ingredient);
    }

    /**
     * Returns true if there are any ingredients in the {@link Fridge} that are expiring before or on the date passed as a parameter
     *
     * @param expireBefore the Date to be compared with the expiry dates of the ingredients stored
     * @return true if the {@link IngredientsList} of the {@link Fridge} contains ingredients expiring before the parameter date,
     * false othewise
     */
    public boolean hasExpiringIngredients(Date expireBefore) {
        return !getExpiredIngredients(expireBefore).isEmpty();
    }

    /**
     * Returns true if there are expired ingredients in the {@link Fridge}
     *
     * @return true if the {@link IngredientsList} of the {@link Fridge} contains expired ingredients, false otherwise
     */
    public boolean hasExpiredIngredients() {
        return hasExpiringIngredients(new Date());
    }

    /**
     * Returns an {@link IngredientsList} containing all the ingredients expiring before the date passed as an argument
     *
     * @param expireBefore {@link Date}
     * @return an {@link IngredientsList} with all the Ingredients stored that expired
     * or are expiring before the date passed as a parameter
     */
    public IngredientsList getExpiredIngredients(Date expireBefore) {
        List<Ingredient> expired = new ArrayList<>();
        if (currentIngredients != null)
            for (Ingredient ingredient : currentIngredients.getAllEntries()) {
                if (!ingredient.getExpiryDate().after(expireBefore)) {
                    expired.add(ingredient);
                }
            }
        return new IngredientsList(expired);
    }

    /**
     * Returns an {@link IngredientsList} containing all the ingredients that are currently expired, considering today's date
     *
     * @return an {@link IngredientsList} with all the Ingredients stored whose expiry date is until today or earlier
     */
    public IngredientsList getExpiredIngredients() {
        return getExpiredIngredients(new Date()); // getting all the expired ingredients using the current date
    }

    /**
     * Removes all the ingredients from the {@link Fridge}, that are expiring before or on the date passed as a parameter
     *
     * @param expireBefore the Date indicating which ingredients to remove
     * @return IngredientsList containing all of the ingredients expiring before or on the date passed as a parameter
     * @throws DukeException
     */
    public IngredientsList removeExpiring(Date expireBefore) {
        assert expireBefore != null;
        List<Ingredient> expired = new ArrayList<>();
        while (hasExpiringIngredients(expireBefore)) {
            for (int i = 0; i < currentIngredients.getAllEntries().size(); i++) {
                if (!currentIngredients.getAllEntries().get(i).getExpiryDate().after(expireBefore)) {
                    expired.add(currentIngredients.getAllEntries().get(i));
                    currentIngredients.getAllEntries().remove(i);
                }
            }
        }
        return new IngredientsList(expired);
    }


    /**
     * Removes all the ingredients from the {@link Fridge}, that are currently expired
     *
     * @return IngredientsList containing all of the ingredients that expired before or are expiring today
     */
    public IngredientsList removeExpired() {
        return removeExpiring(new Date());
    }

    /**
     * Adds an {@link Ingredient} to the {@link Fridge}'s {@link IngredientsList}
     *
     * @param ingredient the {@link Ingredient} to be added to the {@link Fridge}
     */
    public void addIngredient(Ingredient ingredient) {
        currentIngredients.addEntry(ingredient);
    }

    /**
     * Returns the {@link Ingredient} from the {@link IngredientsList} of the {@link Fridge},
     * that is indexed by the integer passed as a parameter
     *
     * @param index an integer, indicating the position of the {@link Ingredient} prompted in the {@link IngredientsList} of the {@link Fridge}
     * @return Ingredient, the needed ingredient
     */
    public Ingredient getIngredient(int index) {
        return currentIngredients.getEntry(index);
    }

    /**
     * Returns the ingredient from the {@link IngredientsList} that has the same name and expiry date if found,
     * otherwise looks for the most recently expiring ingredient with the same name and returns it,
     * throws a {@link DukeException} if no ingredient with the same name is found in the {@link IngredientsList} of the {@link Fridge}
     *
     * @param ingredient the ingredient indicating the name and expiry date of the ingredient searched for in the {@link Fridge}
     * @return Ingredient the corresponding by name ingredient found in the {@link Fridge}
     * @throws DukeException if there is no matching by name ingredient in the current {@link IngredientsList}
     */
    public Ingredient getIngredient(Ingredient ingredient) throws DukeException {
        return currentIngredients.getEntry(ingredient);
    }

    /**
     * Returns the number of {@link Ingredient}s currently in the {@link Fridge}
     *
     * @return an integer indicating the number of {@link Ingredient}s stored in the {@link Fridge}
     */
    public int numberOfIngredients() {
        return currentIngredients.size();
    }

    /**
     * Uses - removes and returns the ingredient used from the {@link IngredientsList} of the {@link Fridge},
     * indicated by the list index passed as a parameter
     *
     * @param index an integer indicating the position of the {@link Ingredient} to be removed in the {@link IngredientsList}
     * @return the removed {@link Ingredient}
     */
    public Ingredient useIngredient(int index) {
        return currentIngredients.removeEntry(index);
    }

    /**
     * Returns true if the amount of the ingredient passed as an argument can be used- removed form the {@link IngredientsList}
     *
     * @param toUse an {@link Ingredient} indicating the name and amount to be used
     * @return true if the {@link Ingredient} passed as a parameter could be used, false otherwise
     */
    public boolean useIngredient(Ingredient toUse) {
        return currentIngredients.removeEntry(toUse);
    }
}
