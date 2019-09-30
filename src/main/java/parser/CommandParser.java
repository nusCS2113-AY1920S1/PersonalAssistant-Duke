package parser;

import javafx.util.Pair;
import wrapper.CommandPair;

import java.util.*;

public class CommandParser {

    public enum Commands {search , movies , tvshows , cast , none ,undef , more , help, view , profile , filters , preferences , watchlist}
    public static String[] RootCommandStr = {"search" , "more" , "help" , "view"};

    public static String[] SubCommandStr = {"movies" , "tvshows" , "cast" , "profile" , "filters" , "preferences" , "watchlist"};


    public static TreeMap<String , ArrayList<String>> flagMap = new TreeMap<String, ArrayList<String>>();


    /**
     * Entry point to command parser Class
     *
     * @param command command that was entered by the user
     */
    public static void parseCommands(String command){
        command = command.toLowerCase();
        String commandArr[] = command.split(" ");

        CommandPair UserCommand = obtainCommands(commandArr);
        TreeMap<String , ArrayList<String>> flags = processFlags(commandArr);
        String PayLoad = processPayload(UserCommand , commandArr);


        System.out.println(UserCommand.getRoot());
        System.out.println(UserCommand.getSub());
        System.out.println(PayLoad);

        if(UserCommand.getRoot() == Commands.undef){
            commandSpellChecker(commandArr[0] ,true);
        }

        if(UserCommand.getSub() == Commands.undef){
            commandSpellChecker(commandArr[1] ,false);
        }

    }


    public static CommandPair obtainCommands(String[] Command){
        Commands root = rootCommand(Command[0]);
        Commands subroot = subCommand(root , Command);
        return new CommandPair( root , subroot);
    }

    public static Commands rootCommand(String root){

        switch(root){
            case "search":
                return Commands.search;
            case "view":
                return Commands.view;
            case "help":
                return Commands.help;
            case "more":
                return Commands.more;
            default:
                return Commands.undef;

        }
    }



    public static Commands subCommand(Commands root , String[] CommandArr){

        switch(root){
            case search:

                switch(CommandArr[1]){
                    case "movies":
                        return Commands.movies;
                    case "tvshows":
                        return Commands.tvshows;
                    case "cast":
                        return Commands.cast;
                    default:
                        return Commands.undef;
                }
            case view:
                switch(CommandArr[1]){
                    case "profile":
                        return Commands.profile;
                    case "filters":
                        return Commands.filters;
                    case "preferences":
                        return Commands.preferences;
                    case "watchlist":
                        return Commands.watchlist;
                    default:
                        return Commands.undef;
                }
            default:
                return Commands.none;

        }
    }


    public static TreeMap<String , ArrayList<String>> processFlags(String commandArr[]){
        TreeMap<String , ArrayList<String>> flagMap = new TreeMap<String , ArrayList<String>>();
        String f = "";
        boolean found = false;
        for(String s : commandArr){
            System.out.println(s);
            if(found && !s.matches("-[a-z]")){
                System.out.println("Added");
                System.out.println(f);
                System.out.println(s);
                ArrayList<String> listOfString = flagMap.get(f);
                if (listOfString == null) {
                    listOfString = new ArrayList<String>();
                    listOfString.add(s);
                    flagMap.put(f, listOfString);

                }else{
                    listOfString.add(s);
                }

            }
            if(s.matches("-[a-z]")) {
                System.out.println("FOund");
                System.out.println(s);
                f = s;
                flagMap.put(f, new ArrayList<String>());
                found = true;
            }
        }
        for (Map.Entry<String, ArrayList<String>> entry : flagMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }

        return flagMap;
    }


    public static String processPayload(CommandPair userCommand , String []CommandArr){
        if(userCommand.getRoot() != Commands.none){
            if(userCommand.getSub() != Commands.none){
                return getPayload(2 , CommandArr);
            }else{
                return getPayload(1 , CommandArr);
            }
        }else{
            return "";
        }
    }

    public static String getPayload(int start , String []CommandArr){
        int i  = 0;
        while( i < CommandArr.length && !CommandArr[i].matches("-[a-z]")){
            i++;
        }
        String payload = "";
        for(int j = start;j < i ; j++ ){
            payload += CommandArr[j];
            payload += " ";
        }
        return payload.trim();
    }

    private static void commandSpellChecker(String undefinedCommand , boolean Root){
        System.out.println("Cant find anything");
        double score = -1;
        String MostSimilar = "";
        if(Root){
            for (String s : RootCommandStr){
                double temp = calculateJaccardSimilarity(s , undefinedCommand);
                if (temp > score){
                    MostSimilar = s;
                    score = temp;
                }
            }
            System.out.println("Did you mean" + MostSimilar);
        }else{
            for (String s : SubCommandStr){
                double temp = calculateJaccardSimilarity(s , undefinedCommand);
                if (temp > score){
                    MostSimilar = s;
                    score = temp;
                }
            }
            System.out.println("Did you mean" + MostSimilar);
        }

    }


    private static Double calculateJaccardSimilarity(CharSequence left, CharSequence right) {
        Set<String> intersectionSet = new HashSet<String>();
        Set<String> unionSet = new HashSet<String>();
        boolean unionFilled = false;
        int leftLength = left.length();
        int rightLength = right.length();
        if (leftLength == 0 || rightLength == 0) {
            return 0d;
        }

        for (int leftIndex = 0; leftIndex < leftLength; leftIndex++) {
            unionSet.add(String.valueOf(left.charAt(leftIndex)));
            for (int rightIndex = 0; rightIndex < rightLength; rightIndex++) {
                if (!unionFilled) {
                    unionSet.add(String.valueOf(right.charAt(rightIndex)));
                }
                if (left.charAt(leftIndex) == right.charAt(rightIndex)) {
                    intersectionSet.add(String.valueOf(left.charAt(leftIndex)));
                }
            }
            unionFilled = true;
        }
        return Double.valueOf(intersectionSet.size()) / Double.valueOf(unionSet.size());
    }



}
