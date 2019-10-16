package duke.list.recipelist;

import duke.task.recipetasks.PrepStep;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;

public class PrepStepList {
    private static String msg = "";
    private ArrayList<PrepStep> prepStepList;

    public PrepStepList() {
        this.prepStepList = new ArrayList<>();
    }

    public PrepStepList(ArrayList<PrepStep> prepStepList) {
        this.prepStepList = prepStepList;
    }

    public ArrayList<String> listPrepStep() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add(prepStepList.get(i).toString());
        }
        return arrList;
    }

    public void addPrepStep(String prepStep) throws ParseException {
        prepStepList.add(new PrepStep(getSize() + 1, prepStep));
        int index = prepStepList.size();
        if (index == 1) {
            msg = " prep step in the list.";
        } else {
            msg = " prep steps in the list";
        }
        System.out.println(MESSAGE_ADDED + "       " + prepStepList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    // delete prep step by index on list
    public void deletePrepStep(int i) {
        if (prepStepList.size() - 1 <= 1) {
            msg = " prep step in the list.";
        } else {
            msg = " prep steps in the list";
        }
        System.out.println(MESSAGE_DELETE + "       " + prepStepList.get(i)
                + "\n" + MESSAGE_ITEMS1 + (prepStepList.size() - 1) + msg);
        prepStepList.remove(prepStepList.get(i));
    }

    public int getSize() {
        return prepStepList.size();
    }

    public ArrayList<PrepStep> getPrepStepList() {
        return prepStepList;
    }
}
