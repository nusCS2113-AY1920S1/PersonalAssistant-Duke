package EPparser;

import commands.*;
import Execution.CommandStack;
import MovieUI.Controller;
import MovieUI.MovieHandler;
import wrapper.CommandPair;
import commands.COMMANDKEYS;

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


        switch(command.getRootCommand()){
            case search:
                System.out.println("Search");
                SearchCommand sc = new SearchCommand(UIController);
                sc.initCommand(CommandArr , Command, command.getSubRootCommand());
                System.out.println(sc.getRoot());
                System.out.println(sc.getSubRootCommand());
                CommandStack.pushCmd(sc);
                break;
            case view:
                System.out.println("View");
                ViewCommand vc = new ViewCommand(UIController);
                vc.initCommand(CommandArr , Command, command.getSubRootCommand());
                CommandStack.pushCmd(vc);
                break;
            case help:
                System.out.println("Help");
                HelpCommand hc = new HelpCommand(UIController);
                hc.initCommand(CommandArr, Command, command.getSubRootCommand());
                CommandStack.pushCmd(hc);
                break;
            case more:
                System.out.println("More");
                MoreCommand mc = new MoreCommand(UIController);
                mc.initCommand(CommandArr, Command, command.getSubRootCommand());
                CommandStack.pushCmd(mc);
                break;
            case yes:
                System.out.println("Yes");
                YesCommand yc = new YesCommand(UIController);
                yc.initCommand(CommandArr, Command, command.getSubRootCommand());
                CommandStack.pushCmd(yc);
                break;
            case add:
                System.out.println("Yes");
                AddCommand wc = new AddCommand(UIController);
                wc.initCommand(CommandArr, Command, command.getSubRootCommand());
                break;
            case set:
                System.out.println("Set");
                SetCommand stc = new SetCommand(UIController);
                stc.initCommand(CommandArr, Command , command.getSubRootCommand());
                CommandStack.pushCmd(stc);
                break;
            case playlist:
                System.out.println("Playlist");
                PlaylistCommand pc = new PlaylistCommand(UIController);
                pc.initCommand(CommandArr, Command , command.getSubRootCommand());
                CommandStack.pushCmd(pc);
                break;
            case preference:
                System.out.println("Preference");
                PreferenceCommand pfc = new PreferenceCommand(UIController);
                pfc.initCommand(CommandArr, Command , command.getSubRootCommand());
                CommandStack.pushCmd(pfc);
                break;
            case restriction:
                System.out.println("Restriction");
                RestrictionCommand rc = new RestrictionCommand(UIController);
                rc.initCommand(CommandArr, Command, command.getSubRootCommand());
                CommandStack.pushCmd(rc);
            case remove:
                System.out.println("REmove");
                RemoveCommand removec = new RemoveCommand(UIController);
                removec.initCommand(CommandArr , Command);

                CommandStack.pushCmd(removec);
                break;
            default:
                CommandPair pair = CommandDebugger.commandSpellChecker(CommandArr , COMMANDKEYS.none , UIController);
        }
    }
    /**
     * Function to get the Root command for the comand
     *
     * @param CommandArr command that was entered by the user in split array form
     * @param Command   command that was entered by the user.
     * @param UIController the controller for the UI
     */
    public static void rootCommand(String[] CommandArr , String Command ,  Controller UIController) throws IOException {

        System.out.print("Whats happening");
        switch(CommandArr[0]){
            case "search":
                System.out.println("Search");
                SearchCommand sc = new SearchCommand(UIController);
                sc.initCommand(CommandArr , Command);
                System.out.println(sc.getRoot());
                System.out.println(sc.getSubRootCommand());
                CommandStack.pushCmd(sc);
                break;
            case "view":
                System.out.println("View");
                ViewCommand vc = new ViewCommand(UIController);
                vc.initCommand(CommandArr , Command);
                CommandStack.pushCmd(vc);
                break;
            case "help":
                System.out.println("Help");
                HelpCommand hc = new HelpCommand(UIController);
                hc.initCommand(CommandArr , Command);
                CommandStack.pushCmd(hc);
                break;
            case "more":
                System.out.println("More");
                MoreCommand mc = new MoreCommand(UIController);
                mc.initCommand(CommandArr , Command);
                CommandStack.pushCmd(mc);
                break;
            case "yes":
                System.out.println("Yes");
                YesCommand yc = new YesCommand(UIController);
                yc.initCommand(CommandArr , Command);
                CommandStack.pushCmd(yc);
                break;
            case "add":
                System.out.println("Add");
                AddCommand wc = new AddCommand(UIController);
                wc.initCommand(CommandArr , Command);
                CommandStack.pushCmd(wc);
                break;
            case "set":
                System.out.println("Set");
                SetCommand stc = new SetCommand(UIController);
                stc.initCommand(CommandArr , Command);
                CommandStack.pushCmd(stc);
                break;
            case "playlist":
                System.out.println("Playlist");
                PlaylistCommand pc = new PlaylistCommand(UIController);
                pc.initCommand(CommandArr , Command);

                CommandStack.pushCmd(pc);
                break;
            case "remove":
                System.out.println("Remove");
                RemoveCommand removec = new RemoveCommand(UIController);
                removec.initCommand(CommandArr , Command);

                CommandStack.pushCmd(removec);
                break;
            case "preference":
                System.out.println("Preference");
                PreferenceCommand pfc = new PreferenceCommand(UIController);
                pfc.initCommand(CommandArr , Command );
                CommandStack.pushCmd(pfc);
                break;
            case "restriction":
                System.out.println("Restriction");
                RestrictionCommand rc = new RestrictionCommand(UIController);
                rc.initCommand(CommandArr, Command);
                CommandStack.pushCmd(rc);
                break;
            default:
                CommandPair pair = CommandDebugger.commandSpellChecker(CommandArr , COMMANDKEYS.none, UIController);
                if(pair.getSubRootCommand() == COMMANDKEYS.none){
                    ((MovieHandler)UIController).setFeedbackText("Did you mean :"+ pair.getRootCommand() );
                }else{
                    ((MovieHandler)UIController).setFeedbackText("Did you mean :"+ pair.getRootCommand() + " " + pair.getSubRootCommand() + " " + String.join(" ", Arrays.copyOfRange(CommandArr,2 , CommandArr.length)));
                }
                processCommand(pair , CommandArr , Command, UIController);
                break;
        }
    }
}
