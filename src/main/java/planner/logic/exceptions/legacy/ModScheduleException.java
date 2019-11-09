package planner.logic.exceptions.legacy;

public class ModScheduleException extends ModException {

    public ModScheduleException(String type1, String type2) {
        super("This " + type1 + " clashes with existing " + type2 + "!");
    }

    public ModScheduleException(String type1) {
        this(type1, "tasks");
    }

    public ModScheduleException() {
        this("module");
    }
}
