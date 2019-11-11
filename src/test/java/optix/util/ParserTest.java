package optix.util;

import optix.commands.ByeCommand;
import optix.commands.Command;
import optix.commands.TabCommand;
import optix.commands.finance.ViewMonthlyCommand;
import optix.commands.finance.ViewProfitCommand;
import optix.commands.parser.AddAliasCommand;
import optix.commands.parser.ListAliasCommand;
import optix.commands.parser.RemoveAliasCommand;
import optix.commands.parser.ResetAliasCommand;
import optix.commands.seats.ReassignSeatCommand;
import optix.commands.seats.SellSeatCommand;
import optix.commands.seats.ViewSeatsCommand;
import optix.commands.shows.EditCommand;
import optix.commands.shows.ListCommand;
import optix.commands.shows.ListDateCommand;
import optix.commands.shows.RescheduleCommand;
import optix.commands.shows.ListShowCommand;
import optix.commands.shows.DeleteCommand;
import optix.commands.shows.AddCommand;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

//@@author OungKennedy
class ParserTest {
    private static File currentDir = new File(System.getProperty("user.dir"));
    private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix");
    private Parser testParser;

    @BeforeEach
    void init() {
        testParser = new Parser(filePath);
        testParser.resetPreferences();
    }

    @Test
    @DisplayName("Test bye command")
    void testBye() {
        Command testByeCommand = null;
        try {
            testByeCommand = testParser.parse("bye");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(testByeCommand.getClass(), ByeCommand.class);
    }

    @Test
    @DisplayName("Test list command")
    void testList() {
        Command testListCommand = null;
        try {
            testListCommand = testParser.parse("list");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(testListCommand.getClass(), ListCommand.class);
    }
    
    @Test
    @DisplayName("Test reset alias command")
    void testResetAlias() {
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse("reset-alias");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), ResetAliasCommand.class);
    }

    @Test
    @DisplayName("Test list alias command")
    void testListAlias() {
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse("list-alias");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), ListAliasCommand.class);
    }

    @Test
    @DisplayName("test help")
    void testHelp() {
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse("help");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), TabCommand.class);
    }

    @Test
    @DisplayName("test archive")
    void testArchive() {
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse("archive");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), TabCommand.class);
    }

    @Test
    @DisplayName("test finance")
    void testFinance() {
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse("finance");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), TabCommand.class);
    }

    @Test
    @DisplayName("invalid single word input")
    void testInvalidSingleWordInput() {
        String invalidInput = "invalidInput";
        Throwable e = null;
        try {
            testParser.parse(invalidInput);
        } catch (Throwable ex) {
            e = ex;
        }
        assertTrue(e instanceof OptixInvalidCommandException);
    }

    @Test
    @DisplayName("test edit command")
    void testEdit() {
        // input will not work but an editcommand is still returned
        String testInput = "edit testShow|testDate|newShowName";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), EditCommand.class);
    }

    @Test
    @DisplayName("test sell command")
    void testSellSeat() {
        // input will not work but an actual command is still returned
        String testInput = "sell input";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), SellSeatCommand.class);
    }

    @Test
    @DisplayName("test view command")
    void testViewSeat() {
        // input will not work but an actual command is still returned
        String testInput = "view input";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), ViewSeatsCommand.class);
    }

    @Test
    @DisplayName("test reschedule command")
    void testReschedule() {
        // input will not work but an actual command is still returned
        String testInput = "reschedule input";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), RescheduleCommand.class);
    }

    @Test
    @DisplayName("test list date command")
    void testparseListDate() {
        // input will not work but an actual command is still returned
        String testInput = "list Jan 2020";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), ListDateCommand.class);
    }

    @Test
    @DisplayName("test list show command")
    void testparseListShow() {
        // input will not work but an actual command is still returned
        String testInput = "list testShow";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), ListShowCommand.class);
    }

    @Test
    @DisplayName("test two words bye command")
    void testByeForTwoWords() {
        String testInput = "bye VV";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), ByeCommand.class);
    }

    @Test
    @DisplayName("test add")
    void testAdd() {
        String testInput = "add poto|25|25/12/2020";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), AddCommand.class);
    }

    @Test
    @DisplayName("test delete")
    void testDelete() {
        String testInput = "delete VV";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), DeleteCommand.class);
    }

    @Test
    @DisplayName("test view profit")
    void testViewProfit() {
        String testInput = "view-profit VV";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), ViewProfitCommand.class);
    }

    @Test
    @DisplayName("test view monthly")
    void testViewMonthly() {
        String testInput = "view-monthly VV";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), ViewMonthlyCommand.class);
    }

    @Test
    @DisplayName("test add alias command")
    void testAddAliasCommand() {
        String testInput = "add-alias VV";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), AddAliasCommand.class);
    }

    @Test
    @DisplayName("test remove alias command")
    void testRemoveAliasCommand() {
        String testInput = "remove-alias VV";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), RemoveAliasCommand.class);
    }

    @Test
    @DisplayName("test two words bye command")
    void testReassignSeatCommand() {
        String testInput = "reassign-seat VV";
        Command outputCommand = null;
        try {
            outputCommand = testParser.parse(testInput);
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        assertEquals(outputCommand.getClass(), ReassignSeatCommand.class);
    }

    @Test
    @DisplayName("invalid two word input")
    void testInvalidTwoWordInput() {
        String invalidInput = "invalidInput VV";
        Throwable e = null;
        try {
            testParser.parse(invalidInput);
        } catch (Throwable ex) {
            e = ex;
        }
        assertTrue(e instanceof OptixInvalidCommandException);
    }

    @Test
    @DisplayName("test add alias- bye")
    void testAddAliasMethod_bye() {
        try {
            testParser.addAlias("VV","bye");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "bye";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- list")
    void testAddAliasMethod_list() {
        try {
            testParser.addAlias("VV","list");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "list";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- help")
    void testAddAliasMethod_help() {
        try {
            testParser.addAlias("VV","help");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "help";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- edit")
    void testAddAliasMethod_edit() {
        try {
            testParser.addAlias("VV","edit");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "edit";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- sell")
    void testAddAliasMethod_sell() {
        try {
            testParser.addAlias("VV","sell");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "sell";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- view")
    void testAddAliasMethod_view() {
        try {
            testParser.addAlias("VV","view");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "view";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- reschedule")
    void testAddAliasMethod_reschedule() {
        try {
            testParser.addAlias("VV","reschedule");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "reschedule";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- add")
    void testAddAliasMethod_add() {
        try {
            testParser.addAlias("VV","add");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "add";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- delete")
    void testAddAliasMethod_delete() {
        try {
            testParser.addAlias("VV","delete");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "delete";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- reassign-seat")
    void testAddAliasMethod_reassign_seat() {
        try {
            testParser.addAlias("VV","reassign-seat");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "reassign-seat";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- show")
    void testAddAliasMethod_show() {
        try {
            testParser.addAlias("VV","show");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "show";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- archive")
    void testAddAliasMethod_archive() {
        try {
            testParser.addAlias("VV","archive");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "archive";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- finance")
    void testAddAliasMethod_finance() {
        try {
            testParser.addAlias("VV","finance");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "finance";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- view-profit")
    void testAddAliasMethod_view_profit() {
        try {
            testParser.addAlias("VV","view-profit");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "view-profit";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- view-monthly")
    void testAddAliasMethod_view_monthly() {
        try {
            testParser.addAlias("VV","view-monthly");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "view-monthly";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- add-alias")
    void testAddAliasMethod_add_alias() {
        try {
            testParser.addAlias("VV","add-alias");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "add-alias";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- remove-alias")
    void testAddAliasMethod_remove_alias() {
        try {
            testParser.addAlias("VV","remove-alias");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "remove-alias";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- list-alias")
    void testAddAliasMethod_list_alias() {
        try {
            testParser.addAlias("VV","list-alias");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "list-alias";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test add alias- reset-alias")
    void testAddAliasMethod_reset_alias() {
        try {
            testParser.addAlias("VV","reset-alias");
        } catch (OptixException e) {
            fail("Should not throw exception");
        }
        String expected = "reset-alias";
        String actual = Parser.commandAliasMap.get("VV");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("invalid add-alias: using command keyword as alias")
    void testInvalidAddAliasInput1() {
        Throwable e = null;
        try {
            testParser.addAlias("delete","add");
        } catch (Throwable ex) {
            e = ex;
        }
        assertTrue(e instanceof OptixException);
    }

    @Test
    @DisplayName("invalid add-alias: using pipe symbol")
    void testInvalidAddAliasInput2() {
        Throwable e = null;
        try {
            testParser.addAlias("d|e","add");
        } catch (Throwable ex) {
            e = ex;
        }
        assertTrue(e instanceof OptixException);
    }

    @Test
    @DisplayName("invalid add-alias: using existing alias")
    void testInvalidAddAliasInput3() {
        Throwable e = null;
        try {
            testParser.addAlias("a","delete");
        } catch (Throwable ex) {
            e = ex;
        }
        assertTrue(e instanceof OptixException);
    }

}