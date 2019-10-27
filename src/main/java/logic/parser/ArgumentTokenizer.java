package logic.parser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ArgumentTokenizer {
    public static final String[] TOKENS = new String[]{"/to", "/at", "/from"};

    public static HashMap<String, String> tokenize(String userInput) {
        ArrayList<Integer> breakpoints = getSortedBreakpoints(userInput);
        String[] argumentStrings = breakByBreakpoins(userInput, breakpoints);
        HashMap<String, String> arguments = getMultimap(argumentStrings);
        return arguments;
    }

    public static ArrayList<Integer> getSortedBreakpoints(String userInput) {
        ArrayList<Integer> breakpoints = new ArrayList<>();
        for (int i = 0; i < TOKENS.length; i++) {
            if (userInput.contains(TOKENS[i])) {
                breakpoints.add(userInput.indexOf(TOKENS[i]));
            }
        }
        breakpoints.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.intValue() - o2.intValue();
            }
        });
        return breakpoints;
    }

    public static String[] breakByBreakpoins(String s, ArrayList<Integer> breakpoints) {
        String[] splites = new String[breakpoints.size() + 1];
        breakpoints.add(0, 0);
        for (int i = 0; i < breakpoints.size(); i++) {
            splites[i] = s.substring(breakpoints.get(i), breakpoints.get(i + 1));
        }
        return splites;
    }

    public static HashMap<String, String> getMultimap(String[] argumentStrings) {
        HashMap<String, String> arguments = new HashMap<>();
        for (int i = 0; i < argumentStrings.length; i++) {
            boolean found = false;
            for (int j = 0; j < TOKENS.length; j++) {
                if (argumentStrings[i].indexOf(TOKENS[j]) == 0) {
                    String value = argumentStrings[i].substring(TOKENS[j].length() + 1);
                    arguments.put(TOKENS[j], value);
                    found = true;
                    break;
                }
            }
            if (!found) {
                arguments.put("", argumentStrings[i]);
            }
        }
        return arguments;
    }
}
