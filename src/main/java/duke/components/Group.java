package duke.components;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {

    private String name;
    private ArrayList<Bar> bars;

    //@@author rohan-av

    public Group(String name, ArrayList<Bar> bars) {
        this.name = name;
        this.bars = bars;
    }

    public String getName() {
        return this.name;
    }

    public int size() {
        return bars.size();
    }

    public Bar get(int i) {
        return bars.get(i);
    }

}
