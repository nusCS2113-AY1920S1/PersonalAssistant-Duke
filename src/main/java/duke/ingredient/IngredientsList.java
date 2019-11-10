package duke.ingredient;

import duke.exception.DukeException;
import duke.list.GenericList;

import java.util.Comparator;
import java.util.List;

/**
 * Represents a sorted by expiry date list of {@link Ingredient}s,
 * extending the {@link GenericList}, used to store and manipulate with {@link Ingredient}s
 *
 * @author Sara Djambazovska
 */
public class IngredientsList extends GenericList<Ingredient> {

    /**
     * Constructor of the class {@link IngredientsList}
     * Creates a new {@link IngredientsList} from the {@link List} of ingredients passed as a parameter,
     * storing it sorted deceasing by expiry date
     *
     * @param ingredientsList the {@link List} linked to the {@link IngredientsList}
     */
    public IngredientsList(List<Ingredient> ingredientsList) {
        super(ingredientsList);
        sortByExpiryDate();
    }

    public IngredientsList() {
        super();
    }

    /**
     * Adds an ingredient to the {@link IngredientsList}. The ingredient is added based on it's name and expiry date,
     * if there is an ingredient in the {@link IngredientsList} with the same name and expiry date,
     * only the amount of the existing ingredient is increased by the ingredient's amount,
     * otherwise, the ingredient is added as a new entry in the {@link IngredientsList}
     *
     * @param ingredient {@link Ingredient} to be added to the list
     */
    @Override
    public void addEntry(Ingredient ingredient) {
        assert ingredient != null;
        try {
            if (genList.contains(ingredient) && this.getEntry(ingredient).getExpiryDate().equals(ingredient.getExpiryDate())) {
                //If the new ingredient is the exact same as what we had, update the amount.
                int currentAmount = this.getEntry(ingredient).getAmount();
                this.getEntry(ingredient).changeAmount(currentAmount + ingredient.getAmount());
            } else
                genList.add(ingredient); // if the ingredient was not in the fridge already or it's expiry date was different than the one stored
        } catch (DukeException e) {
            System.out.println(e);
        }
        sortByExpiryDate();
    }

    /**
     * Returns true id there is a sufficient non expired amount of the ingredient passed as a parameter,
     * the validation is done based on the parameter ingredient's name and amount
     *
     * @param ingredient {@link Ingredient} containing the name and amount that is requested to be checked
     */
    public boolean hasEnough(Ingredient ingredient) {
        assert ingredient != null;
        int currAmount = 0;
        for (Ingredient ing : getAllEntries()) {        //for all ingredients,
            if (ing.equals(ingredient) && !ing.isExpired())   //if it has the same name as the required ingredient(equals on ingredient only checks for name equality)
                // and it has not expired
                currAmount += ing.getAmount();          //adds into count this amount
        }
        return ingredient.getAmount() <= currAmount;        //if needed amount is less than or equal to amount we have, means we have enough
    }

    @Override
    public String toString() {
        String ingredients = genList.size() > 1 ? " ingredients: \n" : " ingredient:\n";
        for (Ingredient ingredient : genList) {
            ingredients += (ingredient.toStringNoWarning() + "\n");
        }
        return ingredients;
    }

    @Override
    public Ingredient getEntry(Ingredient entry) throws DukeException {
        assert entry != null;
        for (Ingredient ingredient : genList) {
            if (ingredient.equalsCompletely(entry))
                return ingredient; //if we have an ingredient with the same name and expiry date, we return that one
        }
        try {
            Ingredient found = genList.get(genList.indexOf(entry)); //otherwise we return the first encountered ingredient in the list that has the same name
            return found;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("The ingredient needed can not be found in the Ingredient List");
        }
    }

    /**
     * Returns the non expired ingredient that equals the ingredient passed as a parameter,
     * equality is based by name comparison only, furthermore, the most recently expiring ingredient is returned
     *
     * @param ingredientNeeded {@link Ingredient} containing the name of the non expired ingredient requested from the {@link IngredientsList}
     */
    public Ingredient getNonExpiredEntry(Ingredient ingredientNeeded) throws DukeException {
        assert ingredientNeeded != null;
        for (Ingredient ingredient : genList) {
            if (ingredientNeeded.equals(ingredient) && !ingredient.isExpired())
                return ingredient;
        }
        throw new DukeException("There is not enough of " + ingredientNeeded.getName() + " that is not expired!");
    }

    /**
     * Sorts the ingredients List by expiry date, with the most recently expiring {@link Ingredient}s coming first
     *
     * @return the sorted {@link IngredientsList}
     */
    public IngredientsList sortByExpiryDate() {
        genList.sort(new Comparator<Ingredient>() { // sorting the ingredients descending by amount
            @Override
            public int compare(Ingredient o1, Ingredient o2) {
                return o1.getExpiryDate().before(o2.getExpiryDate()) ? -1 : 1;
            }
        });
        return this;
    }

    /**
     * Uses a certain amount of an ingredient from the {@link IngredientsList}.
     * The ingredient passed as a parameter indicates the ingredient name and amount to be removed/used, it's date is neglected
     * A search is done to remove the needed amount of the ingredient, while removing the most recently expiring ingredient with the same name
     * Furtermore, only non expired ingredient are used
     *
     * @param ingredient {@link Ingredient} indicating the name and amount to be used
     * @return true if the amount of the ingredient to use was successfully removed, false otherwise
     */
    @Override
    public boolean removeEntry(Ingredient ingredient) {
        assert ingredient != null;
        if (!hasEnough(ingredient)) {
            return false;
        }
        sortByExpiryDate();
        int neededAmount = ingredient.getAmount();
        while (neededAmount > 0) {
            Ingredient toUse = null;
            try {
                toUse = getNonExpiredEntry(ingredient);
            } catch (DukeException e) {
                System.out.println(e);
                return false;
            }
            int amountLeft = toUse.getAmount() - neededAmount;
            if (neededAmount < toUse.getAmount()) {
                toUse.setAmount(amountLeft);
                return true;
            }
            neededAmount -= toUse.getAmount();
            genList.remove(toUse);
        }
        sortByExpiryDate();
        return true;
    }
}
