package duke.logic.parsers.commandparser;

import duke.commons.Messages;
import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.ParseException;

import java.util.logging.Logger;

public abstract class CommandParser<T> {
    protected static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public abstract T parse() throws ApiException;

}
