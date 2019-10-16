package task;

/**
 * This enumeration defines the priority levels supported by Chronologer.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */

public enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    INVALID;

    /**
     * This function obtains a Priority Constant based on the input string.
     *
     * @param priorityString String corresponding to priority level
     * @return Correct priority level
     */
    public static Priority getPriorityLevel(String priorityString) {
        switch (priorityString) {
        case "high":
            return Priority.HIGH;
        case "medium":
            return Priority.MEDIUM;
        case "low":
            return Priority.LOW;
        default:
            return Priority.INVALID;
        }
    }
}
