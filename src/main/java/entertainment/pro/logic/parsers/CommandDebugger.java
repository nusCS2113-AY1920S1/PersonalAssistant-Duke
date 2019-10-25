package entertainment.pro.logic.parsers;

import java.util.HashSet;
import java.util.Set;
import entertainment.pro.ui.Controller;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.model.CommandPair;

public class CommandDebugger {

    //TODO MOVE THIS SOMEWHERE ELSE

    public static CommandPair commandSpellChecker(String[] undefinedCommandArr, COMMANDKEYS root, Controller controller) {

        System.out.println("Cant find anything");
        double score = -1;
        if (root == COMMANDKEYS.none && undefinedCommandArr.length > 0) {
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

        if (root != COMMANDKEYS.none && CommandStructure.cmdStructure.get(root).length != 0 && undefinedCommandArr.length > 1) {
            for (COMMANDKEYS s : CommandStructure.cmdStructure.get(root)) {
                double temp = calculateJaccardSimilarity(s.toString(), undefinedCommandArr[1]);
                if (temp > score) {
                    mostSimilarSub = s;
                    score = temp;
                }
            }
            System.out.println("Did you mean" + mostSimilarSub);
        }

        CommandPair cp = new CommandPair(root, mostSimilarSub);

        if (undefinedCommandArr.length < 1 && CommandStructure.cmdStructure.get(root).length != 0) {
            cp.setValidCommand(false);
        }

        return cp;

    }


    public static Double calculateJaccardSimilarity(CharSequence left, CharSequence right) {
        Set<String> iset = new HashSet<String>();
        Set<String> unionSet = new HashSet<String>();
        boolean isfilled = false;
        int leftLength = left.length();
        int rightLength = right.length();
        if (leftLength == 0 || rightLength == 0) {
            return 0d;
        }

        for (int lefti = 0; lefti < leftLength; lefti++) {
            unionSet.add(String.valueOf(left.charAt(lefti)));
            for (int righti = 0; righti < rightLength; righti++) {
                if (!isfilled) {
                    unionSet.add(String.valueOf(right.charAt(righti)));
                }
                if (left.charAt(lefti) == right.charAt(righti)) {
                    iset.add(String.valueOf(left.charAt(lefti)));
                }
            }
            isfilled = true;
        }
        return Double.valueOf(iset.size()) / Double.valueOf(unionSet.size());
    }

}