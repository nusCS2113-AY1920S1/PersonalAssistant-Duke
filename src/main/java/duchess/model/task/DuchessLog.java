package duchess.model.task;

import java.util.ArrayList;
import java.util.List;

public class DuchessLog {
    private static List<String> duchessLog;

    public DuchessLog() {
        duchessLog = new ArrayList<>();
    }

    public void add(String input) {
        duchessLog.add(input);
    }

    public static List<String> getLog() {
        return duchessLog;
    }
}
