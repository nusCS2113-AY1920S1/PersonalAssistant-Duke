package duke.model.list.recipelist;

import duke.model.task.recipetasks.PrepTime;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;

public class PrepTimeList {
    private static String msg = "";
    private ArrayList<PrepTime> prepTimeList;

    public PrepTimeList() {
        this.prepTimeList = new ArrayList<>();
    }

    public PrepTimeList(ArrayList<PrepTime> prepTimeList) {
        this.prepTimeList = prepTimeList;
    }

    public ArrayList<String> listPrepTime() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add(prepTimeList.get(i).toString());
        }
        return arrList;
    }

    /**
     * Add new preparation time to a recipe
     *
     * @param prepTime String containing the new preparation step
     */
    public void addPrepTime(String prepTime) throws ParseException {
//        prepTimeList.add(new PrepTime(getSize() + 1, prepTime));
//        int index = prepTimeList.size();
//        msg = " preparation time in the list.";
//        System.out.println(MESSAGE_ADDED + "       " + prepTimeList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    /**
     * Delete preparation time by inputting index
     *
     * @param i the index of preparation time to be deleted
     */
    public void deletePrepTime(int i) {
        msg = " preparation time in the list.";
        System.out.println(MESSAGE_DELETE + "       " + prepTimeList.get(i)
                + "\n" + MESSAGE_ITEMS1 + (prepTimeList.size() - 1) + msg);
        prepTimeList.remove(prepTimeList.get(i));
    }

    public int getSize() {
        return prepTimeList.size();
    }

    public ArrayList<PrepTime> getPrepTimeList() {
        return prepTimeList;
    }
}
