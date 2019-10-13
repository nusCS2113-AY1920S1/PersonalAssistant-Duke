package EPparser;

import Commands.*;
import Execution.CommandStack;
import MovieUI.Controller;
import task.Tasks;
import wrapper.CommandPair;
import Commands.COMMAND_KEYS;
import java.util.ArrayList;
import java.io.IOException;
import java.io.IOException;


public class CommandParser {
    /**
     * Entry point to command parser Class
     *
     * @param command command that was entered by the user
     */
    public static void parseCommands(String command , Controller UIController) throws IOException {
        command = command.toLowerCase();
        String commandArr[] = command.split(" ");
        rootCommand(commandArr, UIController);

    }

    public static void processCommand(CommandPair command , String[] CommandArr , Controller UIController) throws IOException {

        switch(command.getRootCommand()){
            case search:
                System.out.println("Search");
                SearchCommand sc = new SearchCommand(UIController);
                sc.initCommand(CommandArr , command.getSubRootCommand());
                System.out.println(sc.getRoot());
                System.out.println(sc.getSubRootCommand());
                CommandStack.pushCmd(sc);
                break;
            case view:
                System.out.println("View");
                ViewCommand vc = new ViewCommand(UIController);
                vc.initCommand(CommandArr , command.getSubRootCommand());
                CommandStack.pushCmd(vc);
                break;
            case help:
                System.out.println("Help");
                HelpCommand hc = new HelpCommand(UIController);
                hc.initCommand(CommandArr, command.getSubRootCommand());
                CommandStack.pushCmd(hc);
                break;
            case more:
                System.out.println("More");
                MoreCommand mc = new MoreCommand(UIController);
                mc.initCommand(CommandArr, command.getSubRootCommand());
                CommandStack.pushCmd(mc);
                break;
            case yes:
                System.out.println("Yes");
                YesCommand yc = new YesCommand(UIController);
                yc.initCommand(CommandArr, command.getSubRootCommand());
                CommandStack.pushCmd(yc);
                break;
            case add:
                System.out.println("Yes");
                WatchlistCommand wc = new WatchlistCommand(UIController);
                wc.initCommand(CommandArr, command.getSubRootCommand());
                break;
            case set:
                System.out.println("Set");
                SetCommand stc = new SetCommand(UIController);
                stc.initCommand(CommandArr, command.getSubRootCommand());
                CommandStack.pushCmd(stc);
                break;
            case playlist:
                System.out.println("Playlist");
                PlaylistCommand pc = new PlaylistCommand(UIController);
                pc.initCommand(CommandArr, command.getSubRootCommand());
                CommandStack.pushCmd(pc);
                break;
            case preference:
                System.out.println("Preference");
                PreferenceCommand pfc = new PreferenceCommand(UIController);
                pfc.initCommand(CommandArr, command.getSubRootCommand());
                CommandStack.pushCmd(pfc);
                break;
            default:
                CommandPair pair = Command_Debugger.commandSpellChecker(CommandArr , COMMAND_KEYS.none , UIController);
        }
    }

    public static void rootCommand(String[] CommandArr , Controller UIController) throws IOException {

        System.out.print("Whats happening");
        switch(CommandArr[0]){
            case "search":
                System.out.println("Search");
                SearchCommand sc = new SearchCommand(UIController);
                sc.initCommand(CommandArr);
                System.out.println(sc.getRoot());
                System.out.println(sc.getSubRootCommand());
                CommandStack.pushCmd(sc);
                break;
            case "view":
                System.out.println("View");
                ViewCommand vc = new ViewCommand(UIController);
                vc.initCommand(CommandArr);
                CommandStack.pushCmd(vc);
                break;
            case "help":
                System.out.println("Help");
                HelpCommand hc = new HelpCommand(UIController);
                hc.initCommand(CommandArr);
                CommandStack.pushCmd(hc);
                break;
            case "more":
                System.out.println("More");
                MoreCommand mc = new MoreCommand(UIController);
                mc.initCommand(CommandArr);
                CommandStack.pushCmd(mc);
                break;
            case "yes":
                System.out.println("Yes");
                YesCommand yc = new YesCommand(UIController);
                yc.initCommand(CommandArr);
                CommandStack.pushCmd(yc);
                break;
            case "add":
                System.out.println("Add");
                WatchlistCommand wc = new WatchlistCommand(UIController);
                wc.initCommand(CommandArr);
                CommandStack.pushCmd(wc);
                break;
            case "set":
                System.out.println("Set");
                SetCommand stc = new SetCommand(UIController);
                stc.initCommand(CommandArr);
                CommandStack.pushCmd(stc);
                break;
            case "playlist":
                System.out.println("Playlist");
                PlaylistCommand pc = new PlaylistCommand(UIController);
                pc.initCommand(CommandArr);
                CommandStack.pushCmd(pc);
                break;
            case "preference":
                System.out.println("Preference");
                PreferenceCommand pfc = new PreferenceCommand(UIController);
                pfc.initCommand(CommandArr);
                CommandStack.pushCmd(pfc);
                break;
            default:
                CommandPair pair = Command_Debugger.commandSpellChecker(CommandArr , COMMAND_KEYS.none, UIController);
                processCommand(pair , CommandArr , UIController);
                break;
        }
    }
}
