package commands;

import java.util.HashSet;
import java.util.Set;

import MovieUI.Controller;
import wrapper.CommandPair;

public class Command_Debugger {

    //TODO MOVE THIS SOMEWHERE ELSE


    public static CommandPair commandSpellChecker(String[] undefinedCommandArr, COMMANDKEYS Root, Controller controller) {
        System.out.println("Cant find anything");
        double score = -1;
        if (Root == COMMANDKEYS.none) {
            for (COMMANDKEYS s : CommandStructure.AllRoots) {
                double temp = calculateJaccardSimilarity(s.toString(), undefinedCommandArr[0]);
                if (temp > score) {
                    Root = s;
                    score = temp;
                }
            }
            if (Root != COMMANDKEYS.none) {
                System.out.println("Did you mean" + Root);
            }
        }
        score = -1;
        COMMANDKEYS MostSimilarSub = COMMANDKEYS.none;
        if (Root != COMMANDKEYS.none && CommandStructure.cmdStructure.get(Root).length != 0) {
            for (COMMANDKEYS s : CommandStructure.cmdStructure.get(Root)) {
                double temp = calculateJaccardSimilarity(s.toString(), undefinedCommandArr[1]);
                if (temp > score) {
                    MostSimilarSub = s;
                    score = temp;
                }
            }
            System.out.println("Did you mean" + MostSimilarSub);
        }

        return new CommandPair(Root, MostSimilarSub);

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
