package duke.ingredient;

import duke.exception.DukeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class IngredientsList
{
    private List<Ingredient> ingredientsList;

    public IngredientsList(List<Ingredient> ingredientsList)
    {
        this.ingredientsList = ingredientsList;
    }
    public IngredientsList()
    {
        this.ingredientsList = new ArrayList<>();
    }

    public void addIngredients(Ingredient ingredients)
    {
        ingredientsList.add(ingredients);
    }
    public int size()
    {
        return ingredientsList.size();
    }
    public Ingredient removeIngredients(int Nb)
    {
        return ingredientsList.remove(Nb);
    }
    public void changeIngredientsDate(int Nb, Date date) throws DukeException
    {
        ingredientsList.get(Nb).changeDate(date);
        /*
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String TodayDate = simpleDateFormat.format(new Date());

        String newDate = Convert.stringToDate(date);
        if (newDate.before(TodayDate)) //if date given is already expired,
        {
            throw new DukeException("Ingredient is already expired");
        }
        else
        {
            ingredientsList.get(Nb).changeDate(date);
        }
        */

    }
    public void changeIngredientName(int Nb, String name)
    {
        ingredientsList.get(Nb).setName(name);
    }
    public void changeIngredientAmount(int Nb, Integer amount)
    {
        ingredientsList.get(Nb).changeAmount(amount);
    }
}

