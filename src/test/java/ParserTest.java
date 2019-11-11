import command.AddBookingCommand;
import command.AddInventoryCommand;
import command.AddRoomCommand;
import command.AddUserCommand;
import command.ApproveCommand;
import command.ByeCommand;
import command.Command;
import command.DeleteBookingCommand;
import command.DeleteRoomCommand;
import command.EditBookingCommand;
import command.FindBookingCommand;
import command.FindBookingIndexCommand;
import command.HelpCommand;
import command.ListBookingDailyCommand;
import command.ListBookingMonthCommand;
import command.ListBookingYearCommand;
import command.ListCommand;
import command.ListRoomCommand;
import command.LoginCommand;
import command.LogoutCommand;
import command.RejectCommand;
import command.RemoveUserCommand;
import control.Parser;
import exception.DukeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    private String input;
    private Command testCommand;

    @Test
    void testBye() {
        input = "list";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), ListCommand.class);
    }

    @Test
    void testaddbooking() {
        input = "add amos project /at SR1 /from 22/12/2019 1800 /to 1900";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), AddBookingCommand.class);
    }

    @Test
    void testaddinventory() {
        input = "addinventory chairs /qty 2 /in SR1";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), AddInventoryCommand.class);
    }

    @Test
    void testaddroom() {
        input = "addroom SR1 50";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), AddRoomCommand.class);
    }

    @Test
    void testadduser() {
        input = "adduser Bob";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), AddUserCommand.class);
    }


    @Test
    void testapprove() {
        input = "approve 1";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), ApproveCommand.class);
    }

    @Test
    void testbye() {
        input = "bye";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), ByeCommand.class);
    }

    @Test
    void testdeletebooking() {
        input = "delete 1";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), DeleteBookingCommand.class);
    }

    @Test
    void testdeleteroom() {
        input = "deleteroom 1";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), DeleteRoomCommand.class);
    }

    @Test
    void testeditbooking() {
        input = "edit 1 study";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), EditBookingCommand.class);
    }

    @Test
    void testfindbooking() {
        input = "find meeting";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), FindBookingCommand.class);
    }

    @Test
    void testfindbookingbyindex() {
        input = "findindex 1";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), FindBookingIndexCommand.class);
    }

    @Test
    void testhelp() {
        input = "help";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), HelpCommand.class);
    }

    @Test
    void testlistday() {
        input = "listday 22/12/2019";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), ListBookingDailyCommand.class);
    }

    @Test
    void testlistmonth() {
        input = "listmonth 12";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), ListBookingMonthCommand.class);
    }

    @Test
    void testlistyear() {
        input = "listyear 22/12/2019";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), ListBookingYearCommand.class);
    }

    @Test
    void testlistroom() {
        input = "listroom";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), ListRoomCommand.class);
    }


    @Test
    void testlogin() {
        input = "login alex";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), LoginCommand.class);
    }

    @Test
    void testlogout() {
        input = "logout";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), LogoutCommand.class);
    }

    @Test
    void testreject() {
        input = "reject 1";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), RejectCommand.class);
    }

    @Test
    void testremoveuser() {
        input = "rmuser alex";
        try {
            testCommand = Parser.parse(input);
        } catch (DukeException | IOException e) {
            fail("Exception should not be thrown");
        }
        assertEquals(testCommand.getClass(), RemoveUserCommand.class);
    }
}
