package duke.enums;

public enum ErrorMessages {
    TASKNUM_MUST_BE_INT("     (>_<) OOPS!!! The task number must be an integer!"),
    TASKNUM_INVALID_INT("     (>_<) OOPS!!! Invalid task number."),
    TASKNUM_IS_EMPTY("     (>_<) OOPS!!! The task number cannot be empty."),
    TASKTYPE_IS_EMPTY("     (>_<) OOPS!!! The task's type cannot be empty."),
    KEYWORD_IS_EMPTY("     (>_<) OOPS!!! The keyword cannot be empty."),
    FIXEDDURATION_FORMAT("Format is in: fixedduration <task> /for <duration> <unit>"),
    PRIORITY_FORMAT("     (>_<) OOPS!!! Format is in: setpriority <taskNum> <Priority>"),
    CONTACT_FORMAT("Format is in: addcontact <name>, <contact>, <email>, <office>, put 'nil' if field is empty"),
    CONTACT_INDEX("     (>_<) OOPS!!! The contact index cannot be empty."),
    INVALID_EMAIL_ALERT("     Email needs to have '@'"),
    NON_INTEGER_ALERT("     Input is not an integer value!"),
    UNKNOWN_COMMAND("     (>_<) OoPS!!! I'm sorry, but I don't know what that means :-("),
    AVOID_PIPELINE("     (>_<) OoPS!!! I'm sorry, but please do not add | , thanks.");

    public final String message;
    ErrorMessages(String message) {
        this.message = message;
    }
}
