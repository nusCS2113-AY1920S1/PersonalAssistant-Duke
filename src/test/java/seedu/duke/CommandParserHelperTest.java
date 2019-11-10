package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.common.command.Command;
import seedu.duke.common.command.ExitCommand;
import seedu.duke.common.command.FlipCommand;
import seedu.duke.common.command.HelpCommand;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;
import seedu.duke.email.command.EmailFetchCommand;
import seedu.duke.email.command.EmailFilterByTagCommand;
import seedu.duke.email.command.EmailListCommand;
import seedu.duke.email.command.EmailShowCommand;
import seedu.duke.email.command.EmailTagCommand;
import seedu.duke.email.entity.Email;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CommandParserHelperTest {
    @Test
    public void parseEmailCommandTest() {
        Email emailOne = new Email("TestEmailOne");
        Email emailTwo = new Email("TestEmailTwo");
        Email emailThree = new Email("TestEmailThree");

        EmailList emailList = new EmailList();
        emailList.add(emailOne);
        emailList.add(emailTwo);
        emailList.add(emailThree);

        Model model = Model.getInstance();
        model.setIsUpdateGui(false);
        model.setEmailList(emailList);

        ArrayList<Command.Option> optionListEmpty = new ArrayList<>();
        //ArrayList<Command.Option> optionListCorrect = new ArrayList<>(Arrays.asList(new Command.Option(
        //        "msg", "do after description")));
        ArrayList<Command.Option> optionListExtra = new ArrayList<>(Arrays.asList(new Command.Option(
                "msg", "do after description"), new Command.Option("tag", "123")));

        try {
            Class<?> parser = Class.forName("seedu.duke.common.parser.CommandParseHelper");
            Method method = parser.getDeclaredMethod("parseEmailCommand", String.class, ArrayList.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "email show 1", null) instanceof EmailShowCommand);
            assertTrue(method.invoke(null, "email show 4", null) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "email show -1", null) instanceof InvalidCommand);

            assertTrue(method.invoke(null, "email flip", null) instanceof FlipCommand);
            assertTrue(method.invoke(null, "email bye", null) instanceof ExitCommand);
            assertTrue(method.invoke(null, "email help", null) instanceof HelpCommand);
            assertTrue(method.invoke(null, "email fetch", null) instanceof EmailFetchCommand);

            assertTrue(method.invoke(null, "email list", optionListExtra) instanceof EmailFilterByTagCommand);
            assertTrue(method.invoke(null, "email list", optionListEmpty) instanceof EmailListCommand);

            assertTrue(method.invoke(null, "email update 1", optionListEmpty) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "email update", optionListExtra) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "email update 4", optionListExtra) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "email update 1", optionListExtra) instanceof EmailTagCommand);

        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            fail("No such method");
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        }
    }

}
