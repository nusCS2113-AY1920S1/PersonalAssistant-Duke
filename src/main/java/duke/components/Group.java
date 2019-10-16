package duke.components;

import java.util.ArrayList;

public class Group {

    private String name;
    private ArrayList<Bar> bars;

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
