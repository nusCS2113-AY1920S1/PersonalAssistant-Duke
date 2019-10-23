package Contexts;

import commands.*;
import EPstorage.Blacklist;
import MovieUI.Controller;
import MovieUI.MovieHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ContextHelper {

    static String getIncompleteWords(String command , Controller controller) {
        String[] splitCommand = command.split(" ");

        String incompleteCommand = "";
        if (splitCommand.length == 0) {
            return "";
        } else if (splitCommand.length <= 2) {
            int lastIndex = splitCommand.length;
            lastIndex -= 1;
            incompleteCommand = splitCommand[lastIndex];
        } else {

            String processedCommand = String.join(" ",
                    Arrays.copyOfRange(splitCommand , 2 , splitCommand.length));

            String commandFlagSplit[] = processedCommand.split("-[a-z]");
            String lastinput[] = commandFlagSplit[commandFlagSplit.length - 1].split(",");
            incompleteCommand = lastinput[lastinput.length - 1 ];

        }
        return incompleteCommand;
    }

    static int subStringIndex(String a , String b) {
        int counter = 0;
        for (int i = 0;; i++) {
            if (i >= a.length() || i >= b.length()) {
                break;
            }
            if (!(a.charAt(i) == b.charAt(i))) {
                break;
            }
            counter++;
        }
        return counter;
    }

    static String completeCommand(ArrayList<String> allPossibilities, String incompleteCommand) {
        if (allPossibilities.size() == 0) {
            return "";
        }
        int lengthOfLongestCommonSubstring = allPossibilities.get(0).length();
        for (int i = 0; i < allPossibilities.size() - 1; i++) {
            int commonSubIndex = subStringIndex(allPossibilities.get(i).toLowerCase(),
                    allPossibilities.get(i + 1).toLowerCase());
            if (commonSubIndex < lengthOfLongestCommonSubstring) {
                lengthOfLongestCommonSubstring = commonSubIndex;
            }
        }

        System.out.println(allPossibilities.get(0));
        System.out.println(incompleteCommand.length());
        System.out.println(lengthOfLongestCommonSubstring);

        String completed = allPossibilities.get(0).substring(incompleteCommand.length(),
                lengthOfLongestCommonSubstring);

        return completed.trim();

    }


    public static ArrayList<String> getAllHints(String command , Controller controller) {
        String [] splitCommand = command.toLowerCase().split(" ");
        String incompleteCommand = getIncompleteWords(command.toLowerCase() , controller);

        if (splitCommand.length == 0) {
            return CommandContext.getRoot();
        } else if (splitCommand.length == 1 && isRootCommandComplete(splitCommand[0])) {
            ArrayList<String> allPossibilities =  CommandContext.getPossibilitiesSubRootForRoot(splitCommand[0]);
            String update = completeCommand(allPossibilities , "");
            ((MovieHandler) controller).updateTextField(update);
            return allPossibilities;
        } else if (splitCommand.length == 1) {
            ArrayList<String> allPossibilities =  CommandContext.getPossibilitiesRoot(incompleteCommand);
            String update = completeCommand(allPossibilities , incompleteCommand);
            ((MovieHandler) controller).updateTextField(update);
            return allPossibilities;
        } else if (splitCommand.length == 2 && isSubRootCommandComplete(splitCommand[1])) {
            ArrayList<String> hints  = commandSpecificHints(splitCommand[0]);
//            ((MovieHandler)controller).updateTextField(update);

            //TODO NOT COMPELTE and ROBUST
            return hints;
        } else if (splitCommand.length == 2) {
            System.out.println("I TO FIND THE WORLD");
            ArrayList<String> allPossibilities =  CommandContext.getPossibilitiesSubRoot(incompleteCommand);
            String update = completeCommand(allPossibilities , incompleteCommand);
            ((MovieHandler) controller).updateTextField(update);
            return allPossibilities;
        } else {
            ArrayList<String> allPossibilities  = commandSpecificHints(splitCommand[0], splitCommand[1] , incompleteCommand);

            String update = completeCommand(allPossibilities , incompleteCommand);
            ((MovieHandler) controller).updateTextField(update);
            return allPossibilities;
        }

    }

    private static boolean isRootCommandComplete(String root) {
        for (COMMANDKEYS c : CommandStructure.AllRoots) {
            if (c.toString().equals(root)) {
                return true;
            }
        }
        return false;

    }


    private static boolean isSubRootCommandComplete(String subRoot) {
        for (Map.Entry<COMMANDKEYS, COMMANDKEYS[]> e: CommandStructure.cmdStructure.entrySet()) {
            for (COMMANDKEYS a: e.getValue()) {
                if (a.toString().equals(subRoot)) {
                    return true;
                }
            }

        }
        return false;

    }

    private static ArrayList<String> commandSpecificHints(String root) {

        switch(root.toLowerCase().trim()) {
            case("blacklist"):
                System.out.println("BLACKLSITWEDM vervle");
                return Blacklist.getBlackListAll();

            default:
                return new ArrayList<String>() {
                    {
                        add("Do you need help?? Enter (help <Root COMMAND> to learn more about your command)");
                    }
                };

        }

    }

    private static ArrayList<String> commandSpecificHints(String root , String subRoot , String incompleteCommand) {
        switch(root) {
            case("blacklist"):
                ArrayList<String> hints = Blacklist.getBlackListHints(incompleteCommand);
                if (!root.equals("remove")) {
                    hints.addAll(SearchResultContext.getPossibilities(incompleteCommand));
                }
                return hints;
            default:
                return SearchResultContext.getPossibilities(incompleteCommand);
        }

    }


}