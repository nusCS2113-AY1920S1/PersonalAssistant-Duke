package commands;

import java.util.HashSet;
import java.util.Set;
import MovieUI.Controller;
import wrapper.CommandPair;

public class CommandDebugger {

    //TODO MOVE THIS SOMEWHERE ELSE

    public static CommandPair commandSpellChecker(String[] undefinedCommandArr, COMMANDKEYS root, Controller controller) {
        System.out.println("Cant find anything");
        double score = -1;
        if (root == COMMANDKEYS.none) {
            for (COMMANDKEYS s : CommandStructure.AllRoots) {
                double temp = calculateJaccardSimilarity(s.toString(), undefinedCommandArr[0]);
                if (temp > score) {
                    root = s;
                    score = temp;
                }
            }
            if (root != COMMANDKEYS.none) {
                System.out.println("Did you mean" + root);
            }
        }
        score = -1;
        COMMANDKEYS mostSimilarSub = COMMANDKEYS.none;
        if (root != COMMANDKEYS.none && CommandStructure.cmdStructure.get(root).length != 0) {
            for (COMMANDKEYS s : CommandStructure.cmdStructure.get(root)) {
                double temp = calculateJaccardSimilarity(s.toString(), undefinedCommandArr[1]);
                if (temp > score) {
                    mostSimilarSub = s;
                    score = temp;
                }
            }
            System.out.println("Did you mean" + mostSimilarSub);
        }

        return new CommandPair(root, mostSimilarSub);

    }


    public static Double calculateJaccardSimilarity(CharSequence left, CharSequence right) {
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