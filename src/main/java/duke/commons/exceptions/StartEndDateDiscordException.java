package duke.commons.exceptions;

import duke.commons.Messages;

public class StartEndDateDiscordException extends ParseException {
    public StartEndDateDiscordException() {
        super(Messages.START_END_DATE_DISCORD);
    }
}
