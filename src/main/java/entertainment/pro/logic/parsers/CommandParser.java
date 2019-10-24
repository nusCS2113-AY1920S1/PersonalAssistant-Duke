package entertainment.pro.logic.parsers;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.commands.*;
import entertainment.pro.logic.Execution.CommandStack;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.model.CommandPair;

import java.io.IOException;
import java.util.Arrays;


public class CommandParser {
    /**
     * Entry point to command parser Class
     *
     * @param command command that was entered by the user
     */
    public static void parseCommands(String command , Controller UIController) throws IOException {
        command = command.toLowerCase();
        String commandArr[] = command.split(" ");
        rootCommand(commandArr , command, UIController);

    }

    /**
     * Function to get the most probable root and sub root command if there was a typo in the user command
     *
     * @param CommandArr command that was entered by the user in split array form
     * @param Command   command that was entered by the user.
     * @param UIController the controller for the UI
     */
    public static void processCommand(CommandPair command , String[] CommandArr , String Command , Controller UIController) throws IOException {

        if (!command.isValidCommand()) {
            return;
        }


        switch(command.getRootCommand()) {
            case search:
                System.out.println("Search");
                SearchCommand sc = new SearchCommand(UIController);
                sc.initCommand(CommandArr , Command, command.getSubRootCommand());
                System.out.println(sc.getRoot());
                System.out.println(sc.getSubRootCommand());
                if (command.isValidCommand()) {
                    CommandStack.pushCmd(sc);
                }

                break;
            case view:
                System.out.println("View");
                ViewCommand vc = new ViewCommand(UIController);
                vc.initCommand(CommandArr , Command, command.getSubRootCommand());
                if (command.isValidCommand()) {
                    CommandStack.pushCmd(vc);
                }
                break;
            case help:
                System.out.println("Help");
                HelpCommand hc = new HelpCommand(UIController);
                hc.initCommand(CommandArr, Command, command.getSubRootCommand());
                if (command.isValidCommand()) {
                    CommandStack.pushCmd(hc);
                }
                break;
            case more:
                System.out.println("More");
                MoreCommand mc = new MoreCommand(UIController);
                mc.initCommand(CommandArr, Command, command.getSubRootCommand());
                if (command.isValidCommand()) {
                    CommandStack.pushCmd(mc);
                }
                break;
            case yes:
                System.out.println("Yes");
                YesCommand yc = new YesCommand(UIController);
                yc.initCommand(CommandArr, Command, command.getSubRootCommand());
                if (command.isValidCommand()) {
                    CommandStack.pushCmd(yc);
                }
                break;
            case add:
                System.out.println("Yes");
                AddCommand wc = new AddCommand(UIController);
                wc.initCommand(CommandArr, Command, command.getSubRootCommand());
                if (command.isValidCommand()) {
                    CommandStack.pushCmd(wc);
                }
                break;
            case set:
                System.out.println("Set");
                SetCommand stc = new SetCommand(UIController);
                stc.initCommand(CommandArr, Command , command.getSubRootCommand());
                if (command.isValidCommand()) {
                    CommandStack.pushCmd(stc);
                }
                break;
            case playlist:
                System.out.println("Playlist");
                PlaylistCommand pc = new PlaylistCommand(UIController);
                pc.initCommand(CommandArr, Command , command.getSubRootCommand());
                if (command.isValidCommand()) {
                    CommandStack.pushCmd(pc);
                }
                break;
            case preference:
                System.out.println("Preference");
                PreferenceCommand pfc = new PreferenceCommand(UIController);
                pfc.initCommand(CommandArr, Command , command.getSubRootCommand());
                if (command.isValidCommand()) {
                    CommandStack.pushCmd(pfc);
                }
                break;
            case restriction:
                System.out.println("Restriction");
                RestrictionCommand rc = new RestrictionCommand(UIController);
                rc.initCommand(CommandArr, Command, command.getSubRootCommand());
                if (command.isValidCommand()) {
                    CommandStack.pushCmd(rc);
                }
                break;
            case blacklist:
                System.out.println("blacklist");
                BlacklistCommand bbc = new BlacklistCommand(UIController);
                bbc.initCommand(CommandArr , Command);

                if (command.isValidCommand()) {
                    CommandStack.pushCmd(bbc);
                }
                break;
            case watchlist:
                System.out.println("watchlist");
                WatchlistCommand wlc = new WatchlistCommand(UIController);
                wlc.initCommand(CommandArr , Command);

                if (command.isValidCommand()) {
                    CommandStack.pushCmd(wlc);
                }
                break;
            default:
                CommandPair pair = CommandDebugger.commandSpellChecker(CommandArr , COMMANDKEYS.none , UIController);
                ((MovieHandler) UIController).setFeedbackText("Sorry we are unable to process your command. Please check help for more details!");
        }
    }
    /**
     * Function to get the Root command for the comand
     *
     * @param commandArr command that was entered by the user in split array form
     * @param command   command that was entered by the user.
     * @param uicontroller the controller for the UI
     */
    public static void rootCommand(String[] commandArr , String command ,  Controller uicontroller) throws IOException {

        System.out.print("Whats happening");
        switch(commandArr[0]) {
            case "search":
                System.out.println("Search");
                SearchCommand sc = new SearchCommand(uicontroller);
                if (sc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(sc);
                }
                break;
            case "view":
                System.out.println("View");
                ViewCommand vc = new ViewCommand(uicontroller);
                if (vc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(vc);
                }
                break;
            case "help":
                System.out.println("Help");
                HelpCommand hc = new HelpCommand(uicontroller);
                if (hc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(hc);
                }
                break;
            case "more":
                System.out.println("More");
                MoreCommand mc = new MoreCommand(uicontroller);
                if (mc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(mc);
                }
                break;
            case "yes":
                System.out.println("Yes");
                YesCommand yc = new YesCommand(uicontroller);
                if (yc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(yc);
                }
                break;
            case "add":
                System.out.println("Add");
                AddCommand wc = new AddCommand(uicontroller);
                if (wc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(wc);
                }
                break;
            case "set":
                System.out.println("Set");
                SetCommand stc = new SetCommand(uicontroller);
                if (stc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(stc);
                }
                break;
            case "playlist":
                System.out.println("Playlist");
                PlaylistCommand pc = new PlaylistCommand(uicontroller);
                if (pc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(pc);
                }
                break;
            case "remove":
                System.out.println("Remove");
                RemoveCommand removec = new RemoveCommand(uicontroller);
                if (removec.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(removec);
                }
                break;
            case "preference":
                System.out.println("Preference");
                PreferenceCommand pfc = new PreferenceCommand(uicontroller);
                if (pfc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(pfc);
                }
                break;
            case "restriction":
                System.out.println("Restriction");
                RestrictionCommand rc = new RestrictionCommand(uicontroller);
                if (rc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(rc);
                }
                break;
            case "get":
                System.out.println("Get");
                GetCommand gc = new GetCommand(uicontroller);
                if (gc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(gc);
                }
                break;
            case "blacklist":
                System.out.println("blacklist");
                BlacklistCommand bbc = new BlacklistCommand(uicontroller);
                bbc.initCommand(commandArr , command);

                if (bbc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(bbc);
                }

                break;
            case "watchlist":
                System.out.println("watchlist");
                WatchlistCommand wlc = new WatchlistCommand(uicontroller);
                wlc.initCommand(commandArr , command);

                if (wlc.initCommand(commandArr , command)) {
                    CommandStack.pushCmd(wlc);
                }

                break;
            default:
                CommandPair pair = CommandDebugger.commandSpellChecker(commandArr , COMMANDKEYS.none, uicontroller);
                if (pair.getSubRootCommand() == COMMANDKEYS.none) {
                    ((MovieHandler) uicontroller).setAutoCompleteText("Did you mean :"+ pair.getRootCommand());
                } else {
                    ((MovieHandler) uicontroller).setAutoCompleteText("Did you mean :"+ pair.getRootCommand() + " " + pair.getSubRootCommand() + " " + String.join(" ", Arrays.copyOfRange(commandArr,2 , commandArr.length)));
                }
                processCommand(pair , commandArr , command, uicontroller);
                break;
        }
    }
}