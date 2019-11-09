package entertainment.pro.logic.parsers;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.assertions.CommandAssertions;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.commons.exceptions.EmptyCommandException;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.logic.execution.CommandStack;
import entertainment.pro.logic.parsers.commands.*;
import entertainment.pro.model.CommandPair;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CommandParser class to determine the root command given the user input.
 */
public class CommandParser {

    private static Logger logger = Logger.getLogger(CommandParser.class.getName());

    /**
     * Entry point to command parser Class.
     *
     * @param command command that was entered by the user
     */
    public static void parseCommands(String command , Controller uicontroller) throws
            IOException
            , Exceptions
            , EmptyCommandException
            , MissingInfoException {

        if (command.trim().length() == 0) {
            logger.log(Level.SEVERE , PromptMessages.MISSING_COMMAND);
            throw new EmptyCommandException(PromptMessages.MISSING_COMMAND);
        }
        String commandArr[] = command.split(" ");
        rootCommand(commandArr , command, uicontroller);
    }

    /**
     * Function to get the most probable root and sub root command if there was a typo in the user command.
     *
     * @param commandArr command that was entered by the user in split array form
     * @param commandStr   command that was entered by the user.
     * @param uicontroller the controller for the UI
     */
    public static void processCommand(CommandPair command
            , String[] commandArr
            , String commandStr
            , Controller uicontroller)
            throws IOException, Exceptions , MissingInfoException {

        assert (CommandAssertions.assertIsLowerStringArr(commandArr));

        if (!command.isValidCommand()) {
            return;
        }
        switch(command.getRootCommand()) {
        case SEARCH:
            SearchCommand sc = new SearchCommand(uicontroller);
            sc.initCommand(commandArr , commandStr, command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(sc);
            }
            break;
        case VIEW:
            ViewCommand vc = new ViewCommand(uicontroller);
            vc.initCommand(commandArr , commandStr, command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(vc);
            }
            break;
        case HELP:
            HelpCommand hc = new HelpCommand(uicontroller);
            hc.initCommand(commandArr, commandStr, command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(hc);
            }
            break;
        case MORE:
            MoreCommand mc = new MoreCommand(uicontroller);
            mc.initCommand(commandArr, commandStr, command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(mc);
            }
            break;
        case YES:
            YesCommand yc = new YesCommand(uicontroller);
            yc.initCommand(commandArr, commandStr, command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(yc);
            }
            break;
        case ADD:
            AddCommand wc = new AddCommand(uicontroller);
            wc.initCommand(commandArr, commandStr, command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(wc);
            }
            break;
        case SET:
            SetCommand stc = new SetCommand(uicontroller);
            stc.initCommand(commandArr, commandStr , command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(stc);
            }
            break;
        case PLAYLIST:
            PlaylistCommand pc = new PlaylistCommand(uicontroller);
            pc.initCommand(commandArr, commandStr , command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(pc);
            }
            break;
        case PREFERENCE:
            PreferenceCommand pfc = new PreferenceCommand(uicontroller);
            pfc.initCommand(commandArr, commandStr , command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(pfc);
            }
            break;
        case RESTRICTION:
            RestrictionCommand rc = new RestrictionCommand(uicontroller);
            rc.initCommand(commandArr, commandStr, command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(rc);
            }
            break;
        case BLACKLIST:
            BlacklistCommand bbc = new BlacklistCommand(uicontroller);
            bbc.initCommand(commandArr, commandStr, command.getSubRootCommand());

            if (command.isValidCommand()) {
                CommandStack.pushCmd(bbc);
            }
            break;
        case WATCHLIST:
            WatchlistCommand wlc = new WatchlistCommand(uicontroller);
            wlc.initCommand(commandArr, commandStr, command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(wlc);
            }
            break;
        case FIND:
            FindCommand fc = new FindCommand(uicontroller);
            fc.initCommand(commandArr, commandStr, command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(fc);
            }
            break;
        case EXIT:
            ExitCommand ec = new ExitCommand(uicontroller);
            ec.initCommand(commandArr , commandStr , command.getSubRootCommand());
            if (command.isValidCommand()) {
                CommandStack.pushCmd(ec);
            }
            break;
        default:
            CommandPair pair = CommandDebugger.commandSpellChecker(commandArr , COMMANDKEYS.NONE, uicontroller);
            ((MovieHandler) uicontroller).setGeneralFeedbackText(PromptMessages.UNABLE_TO_PROCESS);

        }
    }

    /**
     * Function to get the Root command for the comand.
     *
     * @param commandArr command that was entered by the user in split array form
     * @param command   command that was entered by the user.
     * @param uicontroller the controller for the UI
     */
    public static void rootCommand(String[] commandArr , String command ,
                                   Controller uicontroller) throws IOException, Exceptions , MissingInfoException {

        switch(commandArr[0].toLowerCase()) {
        case "search":
            SearchCommand sc = new SearchCommand(uicontroller);
            if (sc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(sc);
            }
            break;
        case "view":
            ViewCommand vc = new ViewCommand(uicontroller);
            if (vc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(vc);
            }
            break;
        case "help":
            HelpCommand hc = new HelpCommand(uicontroller);
            if (hc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(hc);
            }
            break;
        case "more":
            MoreCommand mc = new MoreCommand(uicontroller);
            if (mc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(mc);
            }
            break;
        case "yes":
            YesCommand yc = new YesCommand(uicontroller);
            if (yc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(yc);
            }
            break;
        case "add":
            AddCommand wc = new AddCommand(uicontroller);
            if (wc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(wc);
            }
            break;
        case "set":
            SetCommand stc = new SetCommand(uicontroller);
            if (stc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(stc);
            }
            break;
        case "playlist":
            PlaylistCommand pc = new PlaylistCommand(uicontroller);
            if (pc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(pc);
            }
            break;
        case "remove":
            RemoveCommand removec = new RemoveCommand(uicontroller);
            if (removec.initCommand(commandArr , command)) {
                CommandStack.pushCmd(removec);
            }
            break;
        case "preference":
            PreferenceCommand pfc = new PreferenceCommand(uicontroller);
            if (pfc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(pfc);
            }
            break;
        case "restriction":
            RestrictionCommand rc = new RestrictionCommand(uicontroller);
            if (rc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(rc);
            }
            break;
        case "get":
            GetCommand gc = new GetCommand(uicontroller);
            if (gc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(gc);
            }
            break;
        case "blacklist":
            BlacklistCommand bbc = new BlacklistCommand(uicontroller);
            if (bbc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(bbc);
            }
            break;
        case "watchlist":
            WatchlistCommand wlc = new WatchlistCommand(uicontroller);
            if (wlc.initCommand(commandArr , command)) {
                CommandStack.pushCmd(wlc);
            }
            break;
        case "find":
            FindCommand fc = new FindCommand(uicontroller);
            if (fc.initCommand(commandArr, command)) {
                CommandStack.pushCmd(fc);
            }
            break;
        case "exit":
            ExitCommand ec = new ExitCommand(uicontroller);
            if (ec.initCommand(commandArr , command)) {
                CommandStack.pushCmd(ec);
            }
            break;
        default:
            CommandPair pair = CommandDebugger.commandSpellChecker(commandArr , COMMANDKEYS.NONE, uicontroller);
            askUserConfirmation(pair , uicontroller , commandArr);
            processCommand(pair , commandArr , command, uicontroller);
            break;
        }
    }

    private static void askUserConfirmation(CommandPair pair, Controller uicontroller, String[] commandArr) {
        if (pair.getSubRootCommand() == COMMANDKEYS.NONE) {
            ((MovieHandler) uicontroller).setAutoCompleteText(PromptMessages.DID_YOU_MEAN + pair.getRootCommandStr());
        } else {
            ((MovieHandler) uicontroller).setAutoCompleteText(PromptMessages.DID_YOU_MEAN + pair.getRootCommandStr() + " "
                    + pair.getSubRootCommandStr() + " "
                    + String.join(" ", Arrays.copyOfRange(commandArr, 2, commandArr.length)));
        }
    }
}
