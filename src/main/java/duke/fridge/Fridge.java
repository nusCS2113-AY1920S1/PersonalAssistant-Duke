package duke.fridge;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Fridge {

    private List<Ingredient> currentIngredients;
    private List<Ingredient> expiredIngredients;

    public Fridge(List<Ingredient> list) {
        this.currentIngredients = list;
        this.expiredIngredients = getExpiredIngredients();
    }

    public Fridge() {
        currentIngredients = new ArrayList<>();
        expiredIngredients = new ArrayList<>();
    }

    public void putIngredient(Ingredient ingredient) {
        if (currentIngredients.contains(ingredient) && currentIngredients.get(currentIngredients.indexOf(ingredient)).getExpiryDate().equals(ingredient.getExpiryDate())) {
            int currentAmount = currentIngredients.get(currentIngredients.indexOf(ingredient)).getAmount();
            currentIngredients.get(currentIngredients.indexOf(ingredient)).changeAmount(currentAmount + ingredient.getAmount());
        } else
            currentIngredients.add(ingredient); // if the ingredient was not in the fridge already or it's expiry date was different than the stored one
    }

    public List<Ingredient> getAllIngredients(){
        return currentIngredients;
    }
    public boolean hasEnough(Ingredient ingredient) {
        int currAmount = 0;
        for (Ingredient ing : currentIngredients) {
            if (ing.equals(ingredient))
                currAmount += ing.getAmount();
        }
        return ingredient.getAmount() < currAmount;
    }

    public void useIngredient(Ingredient ingredient) throws DukeException {
        if (!hasEnough(ingredient)) {
            int amountAvailable = currentIngredients.contains(ingredient) ? currentIngredients.get(currentIngredients.indexOf(ingredient)).getAmount() : 0;
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
            Ingredient toUse = currentIngredients.get(currentIngredients.indexOf(ingredient));
            int amountLeft = toUse.getAmount() - neededAmount;
            if (neededAmount < toUse.getAmount()) {
                toUse.changeAmount(amountLeft);
                return;
            }
            neededAmount -= toUse.getAmount();
            currentIngredients.remove(toUse);
        }
    }

    public boolean hasExpiredIngredients() {
        return !getExpiredIngredients(new Date()).isEmpty();
    }

    public List<Ingredient> getExpiredIngredients(Date expireBefore) {
        List<Ingredient> expired = new ArrayList<>();
        if(currentIngredients!=null)
        for (Ingredient ingredient : currentIngredients) {
            if (!ingredient.getExpiryDate().after(expireBefore)) {
                expired.add(ingredient);
            }
        }
        return expired;
    }

    public List<Ingredient> removeExpiring(Date expireBefore) {
        List<Ingredient> expired = getExpiredIngredients(expireBefore);
        for (Ingredient ingredient : expired) {
            currentIngredients.remove(ingredient);
        }
        return expired;
    }

    public List<Ingredient> removeExpired() {
        return removeExpiring(new Date());
    }

    public List<Ingredient> getExpiredIngredients() {
        return getExpiredIngredients(new Date()); // getting all the expired ingredients using the current date
    }

    public List<Ingredient> getMostRecentlyExpiring(int nbIngredients) {
        currentIngredients.sort(new Comparator<Ingredient>() { // sorting the ingredients by expiry date
            @Override
            public int compare(Ingredient o1, Ingredient o2) {
                if (o1.getExpiryDate().before(o2.getExpiryDate()))
                    return -1;
                return o1.getExpiryDate().equals(o2.getExpiryDate()) ? 0 : 1;
            }
        });
        return nbIngredients > currentIngredients.size() ? currentIngredients : currentIngredients.subList(0, nbIngredients);
    }
}
