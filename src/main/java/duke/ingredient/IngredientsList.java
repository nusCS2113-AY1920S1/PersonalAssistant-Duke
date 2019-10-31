package duke.ingredient;

import duke.exception.DukeException;
import duke.list.GenericList;


import java.util.Comparator;



import java.util.Date;
import java.util.List;

public class IngredientsList extends GenericList<Ingredient> {

    public IngredientsList(List<Ingredient> ingredientsList) {
        super(ingredientsList);
        sortByExpiryDate();
    }

    public IngredientsList() {
        super();
    }

    public void changeIngredientsDate(int Nb, Date date) throws DukeException {
        genList.get(Nb).changeDate(date);
    }


    @Override
    public void addEntry(Ingredient ingredient) {
        if (genList.contains(ingredient) && this.getEntry(ingredient).getExpiryDate().equals(ingredient.getExpiryDate())) {
            int currentAmount = this.getEntry(ingredient).getAmount();
            this.getEntry(ingredient).changeAmount(currentAmount + ingredient.getAmount());
        } else
            genList.add(ingredient); // if the ingredient was not in the fridge already or it's expiry date was different than the stored one
            sortByExpiryDate();
    }

    public boolean hasEnough(Ingredient ingredient) {
        int currAmount = 0;
        for (Ingredient ing : getAllEntries()) {        //for all ingredients,
            if (ing.equals(ingredient)&&!ing.isExpired())           //if they are the ingredient we want, and it has not expired,
                currAmount += ing.getAmount();          //adds into count
        }
        return ingredient.getAmount() <= currAmount;        //if needed amount is less than or equal to amount we have, means we have enough
        // return getAllEntries().contains(ingredient);
    }


    public void changeName(int Nb, String name) {
        genList.get(Nb).setName(name);
    }

    public void changeAmount(int Nb, Integer amount) {
        genList.get(Nb).changeAmount(amount);
    }

    public String toString() {
        String ingredients = " ingredients: \n";
        for (Ingredient ingredient : genList) {
            ingredients += (ingredient.toStringNoWarning() + "\n");
        }
        return ingredients;
    }

    @Override
    public Ingredient getEntry(Ingredient entry) {
        for (Ingredient ingredient : genList) {
            if (ingredient.equalsCompletely(entry))
                return ingredient;
        }
        return genList.get(genList.indexOf(entry));
    }
    public Ingredient getNonExpiredEntry(Ingredient ingredient) throws DukeException {
        for(Ingredient ingredient1:genList){
            if(ingredient.equals(ingredient)&&!ingredient1.isExpired())
                return ingredient1;
        }
        throw new DukeException("There is not enough of "+ ingredient.getName()+" that is not expired!");
    }

    public IngredientsList sortByExpiryDate() {
        genList.sort(new Comparator<Ingredient>() { // sorting the ingredients descending by amount
            @Override
            public int compare(Ingredient o1, Ingredient o2) {
                return o1.getExpiryDate().before(o2.getExpiryDate()) ? -1 : 1;
            }
        });
        return this;
    }

    @Override
    public boolean removeEntry(Ingredient ingredient) throws DukeException {
        if (!hasEnough(ingredient)) {
           // int amountAvailable = genList.contains(ingredient) ? getEntry(ingredient).getAmount() : 0;
            //System.out.println(("There is not a sufficient amount of " + ingredient.getName() + " that is not expired, maybe you could buy some first? "));
       return false;
        }
        sortByExpiryDate();
        int neededAmount = ingredient.getAmount();
        while (neededAmount > 0) {
            Ingredient toUse = getNonExpiredEntry(ingredient);
            int amountLeft = toUse.getAmount() - neededAmount;
            if (neededAmount < toUse.getAmount()) {
                toUse.changeAmount(amountLeft);
                return true;
            }
            neededAmount -= toUse.getAmount();
            genList.remove(toUse);
        }
        sortByExpiryDate();
        return true;
    }
}
