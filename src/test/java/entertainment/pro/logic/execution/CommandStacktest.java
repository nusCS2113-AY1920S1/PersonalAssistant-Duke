package entertainment.pro.logic.execution;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.parsers.commands.BlacklistCommand;
import entertainment.pro.logic.parsers.commands.SearchCommand;
import entertainment.pro.logic.parsers.commands.WatchlistCommand;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CommandStacktest {

    @Test
    public void CommandStack_validInputs_success(){

        try {
            CommandStack.pushCmd(new SearchCommand(null));
            assertEquals(1 , CommandStack.getSize());
            assertEquals(COMMANDKEYS.search ,CommandStack.topCmd().getRoot());

            CommandStack.pushCmd(new BlacklistCommand(null));
            assertEquals(2 , CommandStack.getSize());
            assertEquals(COMMANDKEYS.blacklist ,CommandStack.topCmd().getRoot());


            CommandStack.pushCmd(new WatchlistCommand(null));
            assertEquals(3 , CommandStack.getSize());
            assertEquals(COMMANDKEYS.watchlist ,CommandStack.topCmd().getRoot());
        }catch (IOException e){

        }catch (Exceptions e){

        }

    }

    @Test
    public void CommandStack_invalidInputs_failure(){
        assertEquals(0 ,CommandStack.getSize());

        assertEquals(null,CommandStack.nextCommand());

        assertEquals(null,CommandStack.topCmd());

    }
}
