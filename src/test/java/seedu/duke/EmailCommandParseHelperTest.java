package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.common.command.Command;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.common.model.Model;
import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.email.EmailKeywordPairList;
import seedu.duke.email.EmailList;
import seedu.duke.email.command.EmailAddKeywordCommand;
import seedu.duke.email.command.EmailDeleteCommand;
import seedu.duke.email.command.EmailFilterByTagCommand;
import seedu.duke.email.command.EmailFuzzySearchCommand;
import seedu.duke.email.command.EmailListCommand;
import seedu.duke.email.command.EmailListKeywordCommand;
import seedu.duke.email.command.EmailShowCommand;
import seedu.duke.email.command.EmailTagCommand;
import seedu.duke.email.entity.Email;
import seedu.duke.email.entity.KeywordPair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EmailCommandParseHelperTest {


    @Test
    public void parseEmailFuzzySearchCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.email.parser.EmailCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseEmailFuzzySearchCommand", ArrayList.class, String.class);
            method.setAccessible(true);

            ArrayList<Command.Option> optionList = new ArrayList<>();

            //positive cases
            assertTrue(method.invoke(null, optionList, "fuzzySearch CS2113T") instanceof EmailFuzzySearchCommand);
            assertTrue(method.invoke(null, optionList, "fuzzySearch   CS21") instanceof EmailFuzzySearchCommand);
            assertTrue(method.invoke(null, optionList, "fuzzySearch CS   ") instanceof EmailFuzzySearchCommand);

            //negative cases
            assertTrue(method.invoke(null, optionList, "fuzzySearch") instanceof InvalidCommand);
            assertTrue(method.invoke(null, optionList, "fuzzysearch CS2113") instanceof InvalidCommand);
            assertTrue(method.invoke(null, optionList, "   fuzzySearch cs21") instanceof InvalidCommand);
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

    @Test
    public void parseDeleteCommandTest() {
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

        try {
            Class<?> parser = Class.forName("seedu.duke.email.parser.EmailCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseDeleteCommand", String.class);
            method.setAccessible(true);

            //positive cases
            assertTrue(method.invoke(null, "delete 1") instanceof EmailDeleteCommand);
            assertTrue(method.invoke(null, "delete    2") instanceof EmailDeleteCommand);
            assertTrue(method.invoke(null, "delete 3   ") instanceof EmailDeleteCommand);

            //negative cases
            //no index
            assertTrue(method.invoke(null, "delete") instanceof InvalidCommand);
            //space before command
            assertTrue(method.invoke(null, " delete 2") instanceof InvalidCommand);
            //non-integer index
            assertTrue(method.invoke(null, "delete a") instanceof InvalidCommand);
            //random character after index
            assertTrue(method.invoke(null, "delete 2 a") instanceof InvalidCommand);
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

    @Test
    public void parseEmailListCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.email.parser.EmailCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseEmailListCommand", ArrayList.class, String.class);
            method.setAccessible(true);

            ArrayList<Command.Option> emptyOptionList = new ArrayList<>();
            //positive cases
            assertTrue(method.invoke(null, emptyOptionList, "list") instanceof EmailListCommand);


            ArrayList<Command.Option> optionListCorrect = new ArrayList<>(Arrays.asList(new Command.Option(
                    "tag", "SEP")));
            //positive cases
            assertTrue(method.invoke(null, optionListCorrect, "list") instanceof EmailFilterByTagCommand);
            assertTrue(method.invoke(null, optionListCorrect, "list ") instanceof EmailFilterByTagCommand);
            assertTrue(method.invoke(null, optionListCorrect, " list") instanceof EmailFilterByTagCommand);


            ArrayList<Command.Option> optionListCorrect1 = new ArrayList<>(Arrays.asList(new Command.Option(
                    "tag", "workshop"), new Command.Option("tag", "")));
            //positive cases
            assertTrue(method.invoke(null, optionListCorrect1, "list") instanceof EmailFilterByTagCommand);


            ArrayList<Command.Option> optionListExtra = new ArrayList<>(Arrays.asList(new Command.Option(
                    "tag", "workshop"), new Command.Option("tag", "Internship")));
            //positive case
            assertTrue(method.invoke(null, optionListExtra, "list") instanceof EmailFilterByTagCommand);


            ArrayList<Command.Option> optionListWrong = new ArrayList<>(Arrays.asList(new Command.Option(
                    "", "")));
            //negative case
            //tag list is empty
            ArrayList<String> tagsListEmpty = CommandParseHelper.extractTags(optionListWrong);
            assertTrue(method.invoke(null, optionListWrong, "list") instanceof InvalidCommand);
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

    @Test
    public void parseShowEmailCommandTest() {
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

        try {
            Class<?> parser = Class.forName("seedu.duke.email.parser.EmailCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseShowEmailCommand", String.class);
            method.setAccessible(true);

            //positive cases
            assertTrue(method.invoke(null, "show 1") instanceof EmailShowCommand);
            assertTrue(method.invoke(null, "show    2") instanceof EmailShowCommand);
            assertTrue(method.invoke(null, "show 3   ") instanceof EmailShowCommand);

            //negative cases
            //no index
            assertTrue(method.invoke(null, "show") instanceof InvalidCommand);
            //space before command
            assertTrue(method.invoke(null, " show 2") instanceof InvalidCommand);
            //non-integer index
            assertTrue(method.invoke(null, "show a") instanceof InvalidCommand);
            //random character after index
            assertTrue(method.invoke(null, "show 2 a") instanceof InvalidCommand);
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

    @Test
    public void parseEmailTagCommandTest() {
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

        try {
            Class<?> parser = Class.forName("seedu.duke.email.parser.EmailCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseEmailTagCommand", ArrayList.class, String.class);
            method.setAccessible(true);

            ArrayList<Command.Option> optionListCorrect = new ArrayList<>(Arrays.asList(new Command.Option(
                    "tag", "SEP")));
            //positive cases
            assertTrue(method.invoke(null, optionListCorrect, "update 1") instanceof EmailTagCommand);
            assertTrue(method.invoke(null, optionListCorrect, "update    2") instanceof EmailTagCommand);
            assertTrue(method.invoke(null, optionListCorrect, "update 3   ") instanceof EmailTagCommand);

            //negative cases
            //no index
            assertTrue(method.invoke(null, optionListCorrect, "update") instanceof InvalidCommand);
            //space before command
            assertTrue(method.invoke(null, optionListCorrect, " update 2") instanceof InvalidCommand);
            //non-integer index
            assertTrue(method.invoke(null, optionListCorrect, "update a") instanceof InvalidCommand);
            //random character after index
            assertTrue(method.invoke(null, optionListCorrect, "update 2 a") instanceof InvalidCommand);

            ArrayList<Command.Option> emptyOptionList = new ArrayList<>();
            //negative case
            //optionList is empty
            assertTrue(method.invoke(null, emptyOptionList, "update 2") instanceof InvalidCommand);
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

    @Test
    public void parseEmailAddKeywordCommandTest() {
        ArrayList<String> expressions = new ArrayList<>(Arrays.asList("announcement", "SEP"));
        KeywordPair keywordPair = new KeywordPair("notice", expressions);
        ArrayList<String> expressions1 = new ArrayList<>(Arrays.asList("program", "demo"));
        KeywordPair keywordPair1 = new KeywordPair("notice", expressions1);

        EmailKeywordPairList emailKeywordPairList = new EmailKeywordPairList();
        emailKeywordPairList.add(keywordPair);
        emailKeywordPairList.add(keywordPair1);

        Model model = Model.getInstance();
        model.setIsUpdateGui(false);
        model.setKeywordPairList(emailKeywordPairList);

        try {
            Class<?> parser = Class.forName("seedu.duke.email.parser.EmailCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseEmailAddKeywordCommand", ArrayList.class,
                    String.class);
            method.setAccessible(true);

            ArrayList<Command.Option> optionListCorrect = new ArrayList<>(Arrays.asList(new Command.Option(
                    "exp", "announcement")));
            //positive cases
            assertTrue(method.invoke(null, optionListCorrect, "addKeyword Notice")
                    instanceof EmailAddKeywordCommand);
            assertTrue(method.invoke(null, optionListCorrect, "addKeyword    opportunities")
                    instanceof EmailAddKeywordCommand);
            assertTrue(method.invoke(null, optionListCorrect, "addKeyword opportunities   ")
                    instanceof EmailAddKeywordCommand);
            assertTrue(method.invoke(null, optionListCorrect, "addKeyword notice a b")
                    instanceof EmailAddKeywordCommand);

            //negative cases
            //no index
            assertTrue(method.invoke(null, optionListCorrect, "addKeyword")
                    instanceof InvalidCommand);
            //space before command
            assertTrue(method.invoke(null, optionListCorrect, " addKeyword Notice")
                    instanceof InvalidCommand);

            ArrayList<Command.Option> emptyOptionList = new ArrayList<>();
            //negative case
            //optionList is empty
            assertTrue(method.invoke(null, emptyOptionList, "addKeyword Notice")
                    instanceof InvalidCommand);
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
