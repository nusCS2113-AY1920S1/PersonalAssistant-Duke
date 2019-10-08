package parser;

import Commands.*;
import Execution.CommandStack;
import MovieUI.Controller;
import wrapper.CommandPair;
import Commands.COMMAND_KEYS;

public class CommandParser {

    /**
     * Entry point to command parser Class
     *
     * @param command command that was entered by the user
     */
    public static void parseCommands(String command , Controller UIController) {
        command = command.toLowerCase();
        String commandArr[] = command.split(" ");
        rootCommand(commandArr, UIController);

    }

    public static void processCommand(CommandPair command , String[] CommandArr , Controller UIController){

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
            default:
                CommandPair pair = Command_Debugger.commandSpellChecker(CommandArr , COMMAND_KEYS.none , UIController);


        }

    }

    public static void rootCommand(String[] CommandArr , Controller UIController){

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
            default:
                CommandPair pair = Command_Debugger.commandSpellChecker(CommandArr , COMMAND_KEYS.none, UIController);
                processCommand(pair , CommandArr , UIController);

        }

    }

}