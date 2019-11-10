package duke.model.task.recipetasks;

import duke.model.task.ingredienttasks.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;

public class RequiredIngredients {

    ArrayList<Ingredient> requiredIngredientList;

    public RequiredIngredients() {
        this.requiredIngredientList = new ArrayList<Ingredient>();
    }

    public RequiredIngredients(String requiredIngredientsFromStorage) {
        this.requiredIngredientList = new ArrayList<Ingredient>();
        parseIngredientsFromStorage(requiredIngredientsFromStorage);
    }

    public void parseIngredientsFromStorage(String requiredIngredientsFromStorage) {
        String[] individualIng = requiredIngredientsFromStorage.split("/");
        for (int j = 0; j < individualIng.length; j++) {
            String ingredientName, quantity, unit, additionalInfo, remaining, remaining2;
            String[] split = individualIng[j].split(",", 2);
            if (split.length == 2) {
                ingredientName = split[0].trim();
                remaining = split[1].trim();
                String[] split2 = remaining.split(",", 2);
                if (split2.length == 2) {
                    quantity = split2[0].trim();
                    remaining2 = split2[1].trim();
                    String[] split3 = remaining2.split(",", 2);
                    if (split3.length == 2) {
                        unit = split3[0].trim();
                        additionalInfo = split3[1].trim();
                        this.requiredIngredientList.add(new Ingredient(ingredientName, quantity, unit, additionalInfo));
                    }
                    else if (split3.length == 1) {
                        unit = split3[0].trim();
                        additionalInfo = "No additional information.";
                        this.requiredIngredientList.add(new Ingredient(ingredientName, quantity, unit, additionalInfo));
                    }
                }
            }
        }
    }

    public void insertIngredient(String position, String ingredientName, String quantity, String unit, String additionalInfo) {
        requiredIngredientList.add(Integer.parseInt(position) - DISPLAYED_INDEX_OFFSET, new Ingredient(ingredientName, quantity, unit, additionalInfo));
    }

    public void appendIngredient(String ingredientName, String quantity, String unit, String additionalInfo) {
        requiredIngredientList.add(new Ingredient(ingredientName, quantity, unit, additionalInfo));
    }

    public String deleteIngredient(String position) {
        String deletedIngredientName = requiredIngredientList.get(Integer.parseInt(position) - DISPLAYED_INDEX_OFFSET).getIngredientName();
        requiredIngredientList.remove(Integer.parseInt(position) - DISPLAYED_INDEX_OFFSET);
        return deletedIngredientName;
    }

    public void clearIngredients() {
        requiredIngredientList.clear();
    }

    //@@author wjlingg
    public void removeIngredient(int index) {
        requiredIngredientList.remove(index);
    }

    public int getSize() {
        return requiredIngredientList.size();
    }

    //@@author wjlingg
    public HashMap<String, Double> getAllIngredient() {
        HashMap<String, Double> arrayMap = new HashMap<>();
        for (Ingredient ingredient : requiredIngredientList) {
            arrayMap.put(ingredient.getIngredientName(), ingredient.getMass());
        }
        return arrayMap;
    }

    public String toViewString() {
        String joinedString = "";
        if (requiredIngredientList.isEmpty()) {
            joinedString = "No required ingredient.\n";
        } else {
            int i = 0;
            for (Ingredient ingredient : requiredIngredientList) {
                ++i;
                joinedString = joinedString.concat(i + ".  " + ingredient.toString() + "\n");
            }
        }
        return joinedString;
    }

    public ArrayList<Ingredient> getList() {
        return this.requiredIngredientList;
    }

    //@@author wjlingg
    public ArrayList<String> getRequiredIngredientList () {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Ingredient ingredient : requiredIngredientList) {
            arrayList.add(ingredient.toSaveString());
        }
        return arrayList;
    }

    public String toSaveString() {
        String joinedString = "";
        if (requiredIngredientList.isEmpty()) {
            joinedString = "No required ingredient.";
        } else {
            for (Ingredient Ingredient : requiredIngredientList) {
                if (joinedString.isEmpty()) {
                    joinedString = joinedString.concat(Ingredient.toSaveString());
                } else {
                    joinedString = joinedString.concat(" / ");
                    joinedString = joinedString.concat(Ingredient.toSaveString());
                }
            }
        }
        return joinedString;
    }
}