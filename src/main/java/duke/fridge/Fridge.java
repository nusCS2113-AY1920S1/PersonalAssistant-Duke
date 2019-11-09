package duke.fridge;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.storage.FridgeStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fridge {
    private IngredientsList currentIngredients;
    private IngredientsList expiredIngredients;
    private FridgeStorage fridgeStorage;

    public Fridge(FridgeStorage fridgeStorage) throws DukeException {
        this.fridgeStorage = fridgeStorage;
        currentIngredients = new IngredientsList(fridgeStorage.load().getAllEntries());
        expiredIngredients = getExpiredIngredients();
    }

    public Fridge(IngredientsList list) {
        this.currentIngredients = list;
        this.expiredIngredients = getExpiredIngredients();
    }

    public Fridge() {
        currentIngredients = new IngredientsList();
        expiredIngredients = new IngredientsList();
    }

    public boolean isEmpty(){
        return currentIngredients.isEmpty();
    }
    public IngredientsList getAllIngredients() {
        return currentIngredients;
    }

    public boolean hasEnough(Ingredient ingredient) {
        return currentIngredients.hasEnough(ingredient);
    }

    public boolean hasExpiredIngredients() {
        return hasExpiringIngredients(new Date());
    }

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

    public IngredientsList getExpiredIngredients() {
        return getExpiredIngredients(new Date()); // getting all the expired ingredients using the current date
    }

    public IngredientsList removeExpiring(Date expireBefore) throws DukeException {
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

    private boolean hasExpiringIngredients(Date expireBefore) {
        return !getExpiredIngredients(expireBefore).isEmpty();
    }

    public IngredientsList removeExpired() throws DukeException {
        return removeExpiring(new Date());
    }

    public void addIngredient(Ingredient ingredient) {
        currentIngredients.addEntry(ingredient);
    }

    public Ingredient getIngredient(int index) {
        return currentIngredients.getEntry(index);
    }

    public Ingredient getIngredient(Ingredient ingredient) {
        return currentIngredients.getEntry(ingredient);
    }

    public int numberOfIngredients() {
        return currentIngredients.size();
    }

    public Ingredient useIngredient(int index) {
        return currentIngredients.removeEntry(index);
    }

    public boolean useIngredient(Ingredient toUse) throws DukeException {
        return currentIngredients.removeEntry(toUse);
    }
}
