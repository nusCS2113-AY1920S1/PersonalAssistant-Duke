package spinbox.lists;

import spinbox.items.GradedComponent;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GradeList extends SpinBoxList<GradedComponent> {
    public GradeList() {
        super();
    }

    public GradeList(List<GradedComponent> grades) {
        super(grades);
    }

    /**
     * Invalid Command.
     */
    public GradedComponent mark(int index) throws IndexOutOfBoundsException {
        return null;
    }

    /**
     * Order the grade components based on descending weight.
     */
    static class GradedComponentComparator implements Comparator<GradedComponent> {
        @Override
        public int compare(GradedComponent a, GradedComponent b) {
            return (a.getWeight() > b.getWeight()) ? -1 : 0;
        }
    }

    public void sort() {
        Collections.sort(list, new GradedComponentComparator());
    }
}
