package duke.list.recipelist;

import duke.task.recipetasks.*;

import java.text.ParseException;
import java.util.*;

import static duke.common.Messages.*;

public class RecipeTitleList {

    private static String msg = "";
    private ArrayList<RecipeTitle> recipeTitleList;

    public RecipeTitleList() {
        this.recipeTitleList = new ArrayList<>();
    }

    public RecipeTitleList(ArrayList<RecipeTitle> recipeTitleList) {
        this.recipeTitleList = recipeTitleList;
    }

    public ArrayList<String> listRecipeTitle() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add(recipeTitleList.get(i).toString());
        }
        return arrList;
    }

    public void addRecipeTitle(String recipeTitle) throws ParseException {
        recipeTitleList.add(new RecipeTitle(getSize() + 1, recipeTitle));
        int index = recipeTitleList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + recipeTitleList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    // delete ingredient by index on list
    public void deleteIngredient(int i) {
        if (recipeTitleList.size() - 1 <= 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_DELETE + "       " + recipeTitleList.get(i)
                + "\n" + MESSAGE_ITEMS1 + (recipeTitleList.size() - 1) + msg);
        recipeTitleList.remove(recipeTitleList.get(i));
    }

    public int getSize() {
        return recipeTitleList.size();
    }

    public ArrayList<RecipeTitle> getRecipeTitleList() {
        return recipeTitleList;
    }
}
