package duke.list.recipelist;

import duke.task.recipetasks.Rating;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;

public class RatingList {
    private static String msg = "";
    private ArrayList<Rating> ratingList;

    public RatingList() {
        this.ratingList = new ArrayList<>();
    }

    public RatingList(ArrayList<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public ArrayList<String> listRating() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add(ratingList.get(i).toString());
        }
        return arrList;
    }

    public void addRating(String rating) throws ParseException {
        ratingList.add(new Rating(getSize() + 1, rating));
        int index = ratingList.size();
        if (index == 1) {
            msg = " rating in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " +ratingList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    // delete ingredient by index on list
    public void deleteRating(int i) {
        if (ratingList.size() - 1 <= 1) {
            msg = " rating in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_DELETE + "       " + ratingList.get(i)
                + "\n" + MESSAGE_ITEMS1 + (ratingList.size() - 1) + msg);
        ratingList.remove(ratingList.get(i));
    }

    public int getSize() {
        return ratingList.size();
    }

    public ArrayList<Rating> getRatingList() {
        return ratingList;
    }
}
