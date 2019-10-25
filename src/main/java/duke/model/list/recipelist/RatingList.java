package duke.model.list.recipelist;

import duke.model.task.recipetasks.Rating2;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;

public class RatingList {
    private static String msg = "";
    private ArrayList<Rating2> rating2List;

    public RatingList() {
        this.rating2List = new ArrayList<>();
    }

    public RatingList(ArrayList<Rating2> rating2List) {
        this.rating2List = rating2List;
    }

    public ArrayList<String> listRating() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add(rating2List.get(i).toString());
        }
        return arrList;
    }

    public void addRating(String rating) throws ParseException {
        rating2List.add(new Rating2(getSize() + 1, rating));
        int index = rating2List.size();
        if (index == 1) {
            msg = " rating in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + rating2List.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    // delete ingredient by index on list
    public void deleteRating(int i) {
        if (rating2List.size() - 1 <= 1) {
            msg = " rating in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_DELETE + "       " + rating2List.get(i)
                + "\n" + MESSAGE_ITEMS1 + (rating2List.size() - 1) + msg);
        rating2List.remove(rating2List.get(i));
    }

    public int getSize() {
        return rating2List.size();
    }

    public ArrayList<Rating2> getRating2List() {
        return rating2List;
    }
}
