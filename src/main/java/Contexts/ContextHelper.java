package Contexts;

import MovieUI.Controller;
import MovieUI.MovieHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class ContextHelper {

    static String getIncompleteWords(String Command , Controller controller) {
        String[] splitCommand = Command.split(" ");

        String incompleteCommand = "";
        if(splitCommand.length == 0){
            return "";
        }else if(splitCommand.length <= 2){
            int lastIndex = splitCommand.length;
            lastIndex -= 1;
            incompleteCommand = splitCommand[lastIndex];
        }else{

            String processedCommand = String.join(" ", Arrays.copyOfRange(splitCommand ,2 , splitCommand.length));

            String commandFlagSplit[] = processedCommand.split("-[a-z]");
            String lastinput[] = commandFlagSplit[commandFlagSplit.length-1].split(",");
            incompleteCommand = lastinput[lastinput.length-1];

        }
        return incompleteCommand;
    }

    static int subStringIndex(String a , String b){
        int counter = 0;
        for(int i = 0 ; ; i++){
            if(i>= a.length() || i>=b.length()){
                break;
            }
            if(!(a.charAt(i) == b.charAt(i))){
                break;
            }
            counter++;
        }
        return counter;
    }

    static String completeCommand(ArrayList<String> allPossibilities, String incompleteCommand){
        if(allPossibilities.size() == 0){
            return "";
        }
        int lengthOfLongestCommonSubstring = allPossibilities.get(0).length();
        for(int i =0; i < allPossibilities.size()-1 ; i ++){
            int commonSubIndex = subStringIndex(allPossibilities.get(i).toLowerCase() , allPossibilities.get(i+1).toLowerCase());
            if(commonSubIndex < lengthOfLongestCommonSubstring){
                lengthOfLongestCommonSubstring = commonSubIndex;
            }
        }

        String completed = allPossibilities.get(0).substring(incompleteCommand.length() , lengthOfLongestCommonSubstring);

        return completed.trim();

    }


    public static ArrayList<String> getAllHints(String Command , Controller controller){
        String [] splitCommand = Command.split(" ");
        String incompleteCommand = getIncompleteWords(Command ,controller);

        if(splitCommand.length == 0){
            return CommandContext.getRoot();
        }else if(splitCommand.length == 1){
            ArrayList<String> allPossibilities =  CommandContext.getPossibilitiesRoot(incompleteCommand);
            String update = completeCommand(allPossibilities , incompleteCommand);
            ((MovieHandler)controller).updateTextField(update);
            return allPossibilities;
        }else if(splitCommand.length == 2){
            ArrayList<String> allPossibilities =  CommandContext.getPossibilitiesSubRoot(incompleteCommand);
            String update = completeCommand(allPossibilities , incompleteCommand);
            ((MovieHandler)controller).updateTextField(update);
            return allPossibilities;
        }else{
            ArrayList<String> allPossibilities =  SearchResultContext.getPossibilities(incompleteCommand);

            String update = completeCommand(allPossibilities , incompleteCommand);
            ((MovieHandler)controller).updateTextField(update);
            return allPossibilities;
        }

    }


}
