//@@author pdotdeep

package entertainment.pro.logic.execution;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.parsers.commands.BlacklistCommand;
import entertainment.pro.logic.parsers.commands.SearchCommand;
import entertainment.pro.logic.parsers.commands.WatchlistCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CommandStacktest {

    @Test
    public void commandStack_validInputs_success() throws IOException, Exceptions {

        CommandStack.pushCmd(new SearchCommand(null));
        assertEquals(1, CommandStack.getSize());
        assertEquals(CommandKeys.SEARCH, CommandStack.topCmd().getRoot());

        CommandStack.pushCmd(new BlacklistCommand(null));
        assertEquals(2, CommandStack.getSize());
        assertEquals(CommandKeys.BLACKLIST, CommandStack.topCmd().getRoot());


        CommandStack.pushCmd(new WatchlistCommand(null));
        assertEquals(3, CommandStack.getSize());
        assertEquals(CommandKeys.WATCHLIST, CommandStack.topCmd().getRoot());


    }

    @Test
    public void commandStack_invalidInputs_failure() {

        assertEquals(0, CommandStack.getSize());
        assertEquals(null, CommandStack.nextCommand());
        assertEquals(null, CommandStack.topCmd());

    }

    @BeforeEach
    public void clearStack() {
        CommandStack.clearStack();
    }
}
