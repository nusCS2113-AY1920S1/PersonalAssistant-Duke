package duke.tasks;

import java.util.ArrayList;
import java.util.Date;

public class TentativeEvent {
    public static ArrayList<Date> dates = new ArrayList<>();
    public static String description;

    public TentativeEvent(String desc) {
        description = desc;
    }

    public static void clearTentativeEvent() {
        dates.clear();
        description = null;
    }
}
