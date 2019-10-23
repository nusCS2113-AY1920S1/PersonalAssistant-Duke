package duke.fridge;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Fridge {

    private IngredientsList currentIngredients;
    private IngredientsList expiredIngredients;

    public Fridge(IngredientsList list) {
        this.currentIngredients = list;
        this.expiredIngredients = getExpiredIngredients();
    }

    public Fridge() {
        currentIngredients = new IngredientsList();
        expiredIngredients = new IngredientsList();
    }

    public void putIngredient(Ingredient ingredient) {
        if (currentIngredients.has(ingredient) && currentIngredients.getEntry(ingredient).getExpiryDate().equals(ingredient.getExpiryDate())) {
            int currentAmount = currentIngredients.getEntry(ingredient).getAmount();
            currentIngredients.getEntry(ingredient).changeAmount(currentAmount + ingredient.getAmount());
        } else
            currentIngredients.addEntry(ingredient); // if the ingredient was not in the fridge already or it's expiry date was different than the stored one
    }

    public IngredientsList getAllIngredients(){
        return currentIngredients;
    }
    public boolean hasEnough(Ingredient ingredient) {
        int currAmount = 0;
        for (Ingredient ing : currentIngredients.getAllEntries()) {
            if (ing.equals(ingredient))
                currAmount += ing.getAmount();
        }
        return ingredient.getAmount() < currAmount;
    }

    public void useIngredient(Ingredient ingredient) throws DukeException {
        if (!hasEnough(ingredient)) {
            int amountAvailable = currentIngredients.has(ingredient) ? currentIngredients.getEntry(ingredient).getAmount() : 0;
            throw new DukeException("There is not a sufficient amount of " + ingredient.getName() + ", amount available: " + amountAvailable);
        }
        currentIngredients.sort(new Comparator<Ingredient>() { // sorting the ingredients descending by amount
            @Override
            public int compare(Ingredient o1, Ingredient o2) {
                return o2.getAmount() - o1.getAmount();
            }
        });
        int neededAmount = ingredient.getAmount();
        while (neededAmount > 0) {
            Ingredient toUse = currentIngredients.getEntry(ingredient);
            int amountLeft = toUse.getAmount() - neededAmount;
            if (neededAmount < toUse.getAmount()) {
                toUse.changeAmount(amountLeft);
                return;
            }
            neededAmount -= toUse.getAmount();
            currentIngredients.removeEntry(toUse);
        }
    }

    public boolean hasExpiredIngredients() {
        return !getExpiredIngredients(new Date()).isEmpty();
    }

    public IngredientsList getExpiredIngredients(Date expireBefore) {
        List<Ingredient> expired = new ArrayList<>();
        if(currentIngredients!=null)
        for (Ingredient ingredient : currentIngredients.getAllEntries()) {
            if (!ingredient.getExpiryDate().after(expireBefore)) {
                expired.add(ingredient);
            }
        }
        return new IngredientsList(expired);
    }

    public IngredientsList removeExpiring(Date expireBefore) {

        IngredientsList expired = getExpiredIngredients(expireBefore);
        for (Ingredient ingredient : expired.getAllEntries()) {
            currentIngredients.removeEntry(ingredient);
        }
        return expired;
    }

    public IngredientsList removeExpired() {
        return removeExpiring(new Date());
    }

    public IngredientsList getExpiredIngredients() {
        return getExpiredIngredients(new Date()); // getting all the expired ingredients using the current date
    }

    public IngredientsList getMostRecentlyExpiring(int nbIngredients) {
        currentIngredients.sort(new Comparator<Ingredient>() { // sorting the ingredients by expiry date
            @Override
            public int compare(Ingredient o1, Ingredient o2) {
                if (o1.getExpiryDate().before(o2.getExpiryDate()))
                    return -1;
                return o1.getExpiryDate().equals(o2.getExpiryDate()) ? 0 : 1;
            }
        });
        return nbIngredients > currentIngredients.size() ? currentIngredients : new IngredientsList(currentIngredients.getAllEntries().subList(0, nbIngredients));
    }
}
