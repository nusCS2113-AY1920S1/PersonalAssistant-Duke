package leduc;

import leduc.command.*;
import leduc.exception.NonExistentDateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Represents a JUnit test class for the Parser.
 */
public class ParserTest {

    /**
     * Represents a JUnit test method to test if the parser could
     * create the right command depending on the input String (user).
     */
    @Test
    public void commandCreatedTest(){
        Parser parser = new Parser();
        assertTrue(parser.parse("ok") instanceof MeaninglessCommand);
        assertTrue(parser.parse("list") instanceof ListCommand);
        assertFalse(parser.parse("listlist") instanceof ListCommand);
        assertTrue(parser.parse("find eizae") instanceof FindCommand);
        assertTrue(parser.parse("done 12") instanceof DoneCommand);
        assertFalse(parser.parse("done okk") instanceof DoneCommand);
        assertTrue(parser.parse("delete 12") instanceof DeleteCommand);
        assertFalse(parser.parse("delete e") instanceof DeleteCommand);
        assertTrue(parser.parse("todo ekzoa") instanceof TodoCommand);
        assertTrue(parser.parse("deadline d1")instanceof DeadlineCommand);
        assertTrue(parser.parse("event e") instanceof EventCommand);
        assertTrue(parser.parse("bye")instanceof ByeCommand);
        assertFalse(parser.parse("bye " )instanceof ByeCommand);
    }

    /**
     * Represents a JUnit test method to test the method stringToDate.
     * Test if this method could convert string to a Date type only in case of
     * the date really exist.
     */
    @Test
    public void stringToDateTest() {
        Parser parser = new Parser();
        Ui ui = new Ui();

        boolean test1 = false;
        try{ // no catch
            parser.stringToDate("12/12/1222 22:22",ui);
        }
        catch (NonExistentDateException e){
            test1 = true;
        }
        assertFalse(test1);



        boolean test2 = false;
        try{ // catch
            parser.stringToDate("31/11/1222 22:22",ui);
        }
        catch (NonExistentDateException e){
            test2 = true;
        }
        assertTrue(test2);



        boolean test3 = false;
        try{ // catch
            parser.stringToDate("35/12/1222 22:22",ui);
        }
        catch (NonExistentDateException e){
            test3 = true;
        }
        assertTrue(test3);



        boolean test4 = false;
        try{ // catch
            parser.stringToDate("29/02/2021 22:22",ui);
        }
        catch (NonExistentDateException e){
            test4 = true;
        }
        assertTrue(test4);



        boolean test5 = false;
        try{ // catch
            parser.stringToDate("29/13/2021 22:22",ui);
        }
        catch (NonExistentDateException e){
            test5 = true;
        }
        assertTrue(test5);



        boolean test6 = false;
        try{ // catch
            parser.stringToDate("29/12/2021 25:22",ui);
        }
        catch (NonExistentDateException e){
            test6 = true;
        }
        assertTrue(test6);


        boolean test7 = false;
        try{ // catch
            parser.stringToDate("29/12/2021 24:00",ui);
        }
        catch (NonExistentDateException e){
            test7 = true;
        }
        assertTrue(test7);



        boolean test8 = false;
        try{ // catch
            parser.stringToDate("29/12/2021 23:60",ui);
        }
        catch (NonExistentDateException e){
            test8 = true;
        }
        assertTrue(test8);


        boolean test9 = false;
        try{ // catch
            parser.stringToDate("-29/12/2021 23:60",ui);
        }
        catch (NonExistentDateException e){
            test9 = true;
        }
        assertTrue(test9);

    }
}