package parsers;

import utils.DukeException;

import java.util.HashMap;

public class ArgumentTokenizer {
    //@@author chenyuheng
    /**
     *This method tokenize a string contained of key-value pairs.
     * the format is： arg0 /key1 arg1 /key2 arg2 ...
     * arg0 can be accessed by a null String ("") as the key,
     * other arguments can be accessed by the corresponding keys.
     * @param userInput the String need to tokenize
     * @return a HashMap<String, String> represents the argument multimap
     * @throws DukeException if duplicate keys occurs
     */
    public static HashMap<String, String> getArgumentMultimap(String userInput) throws DukeException {
        HashMap<String, String> argumentMultimap = new HashMap<>();
        for (int i = 0; i < userInput.length(); i++) {
            if (userInput.charAt(i) == '/' || i == userInput.length() - 1) {
                argumentMultimap.put("", userInput.substring(0, i).trim());
                userInput = userInput.substring(i + 1);
                break;
            }
        }
        int j = 0;
        if (userInput.length() > 0) {
            if (userInput.charAt(0) == ' ' || userInput.charAt(0) == '\t') {
                while (userInput.charAt(j++) != '/' && j <userInput.length()) ;
            }
        }
        for (int i = 0, p = 0; i < userInput.length(); i++) {
            if (userInput.charAt(i) == '/' || i == userInput.length() - 1) {
                String pairString = userInput.substring(p, i == userInput.length() - 1 ? i + 1 : i);
                p = i + 1;
                String[] splites = pairString.split(" ", 2);
                if (splites.length < 2) {
                    splites = new String[]{splites[0], ""};
                }
                if (splites[0].trim().equals("")) {
                    throw new DukeException("null key occur, please validate the command.");
                }
                if (argumentMultimap.containsKey(splites[0].trim())) {
                    throw new DukeException("duplicated keys, please validate the command.");
                }
                argumentMultimap.put(splites[0].trim(), splites[1].trim());
                if (i < userInput.length()) {
                    if (userInput.charAt(i) == ' ' || userInput.charAt(i) == '\t') {
                        while (userInput.charAt(j++) != '/' && j <userInput.length()) ;
                    }
                }
            }
        }
        return argumentMultimap;
    }
}
