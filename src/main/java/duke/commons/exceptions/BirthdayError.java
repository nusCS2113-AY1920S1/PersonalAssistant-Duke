package duke.commons.exceptions;

import duke.commons.Messages;

public class BirthdayError extends DukeException {
    public BirthdayError() {
        super(Messages.PROFILE_BIRTHDAY_IN_FUTURE);
    }
}
