package duke.fridge;

import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.storage.FridgeStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Fridge {

    private IngredientsList currentIngredients;
    private IngredientsList expiredIngredients;
    private FridgeStorage fridgeStorage;

    public Fridge(FridgeStorage fridgeStorage) throws DukeException {
        this.fridgeStorage=fridgeStorage;
        currentIngredients=new IngredientsList(fridgeStorage.load().getAllEntries());
        expiredIngredients=getExpiredIngredients();
    }

    public Fridge(IngredientsList list) {
        this.currentIngredients = list;
        this.expiredIngredients = getExpiredIngredients();
    }

    public Fridge() {
        currentIngredients = new IngredientsList();
        expiredIngredients = new IngredientsList();
    }

    public void putIngredient(Ingredient ingredient) throws DukeException {
       currentIngredients.addEntry(ingredient);
               fridgeStorage.update();
    }

    public IngredientsList getAllIngredients(){
        return currentIngredients;
    }

    public boolean hasEnough(Ingredient ingredient) {
        return currentIngredients.hasEnough(ingredient);
    }

    public void useIngredient(Ingredient ingredient) throws DukeException {
        currentIngredients.addEntry(ingredient);
        fridgeStorage.update();
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

    public IngredientsList removeExpiring(Date expireBefore) throws DukeException {
        List<Ingredient> expired = new ArrayList<>();
        for(int i=0;i<currentIngredients.getAllEntries().size();i++){
            if (!currentIngredients.getAllEntries().get(i).getExpiryDate().after(expireBefore)) {
                expired.add(currentIngredients.getAllEntries().get(i));
                currentIngredients.getAllEntries().remove(i);
            }
        }
        fridgeStorage.update();
        return new IngredientsList(expired);
    }

    public IngredientsList removeExpired() throws DukeException {
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
