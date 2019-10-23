package duke.ingredient;

import duke.exception.DukeException;
import duke.list.GenericList;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class IngredientsList extends GenericList<Ingredient>
{


    public IngredientsList(List<Ingredient> ingredientsList)
    {
        super(ingredientsList);
    }
    public IngredientsList()
    {
        super();
    }

    public void changeIngredientsDate(int Nb, Date date) throws DukeException
    {
        genList.get(Nb).changeDate(date);

    }
    public boolean has(Ingredient ingredient){
        return getAllEntries().contains(ingredient);
    }
    public void changeName(int Nb, String name)
    {
        genList.get(Nb).setName(name);
    }
    public void changeAmount(int Nb, Integer amount)
    {
        genList.get(Nb).changeAmount(amount);
    }
}

