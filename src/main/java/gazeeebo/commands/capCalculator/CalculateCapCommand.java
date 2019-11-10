//@@author JasonLeeWeiHern

package gazeeebo.commands.capCalculator;

import gazeeebo.parser.CapCommandParser;

import java.util.ArrayList;
import java.util.Map;

/**
 * Calculate the cap by using a formula.
 */
public class CalculateCapCommand {
    /**
     * Modules without a grade score (S/US/CS) = 0.1.
     */
    private static final double DONT_COUNT_SCORE = 0.1;

    /**
     * Calculate the CAP of all the modules.
     *
     * @param caplist the object that deals
     *                stores semNumber, moduleCode, moduleCredits and CAP score.
     * @return the CAP.
     */
    public double calculateCap(final Map<String,
            ArrayList<CapCommandParser>> caplist) {
        double sumCapMcs = 0;
        int sumMcs = 0;
        double scoreNotToCount = DONT_COUNT_SCORE;
        for (String key : caplist.keySet()) {
            for (int i = 0; i < caplist.get(key).size(); i++) {
                double score = new ConvertGradeToScoreCommand().converter(caplist.get(key).get(i).grade);
                if (score != scoreNotToCount) {
                    sumCapMcs += caplist.get(key).get(i).moduleCredit * score;
                    sumMcs += caplist.get(key).get(i).moduleCredit;
                }
            }
        }
        double cap = sumCapMcs / sumMcs;
        return cap;
    }

    /**
     * Calculate the CAP of the particular sem.
     *
     * @param caplist   the object that deals stores
     *                  semNumber, moduleCode, moduleCredits and CAP.
     * @param semNumber the sem which you want to find the CAP.
     * @return the CAP.
     */
    public double calculateCapPerSem(final Map<String,
            ArrayList<CapCommandParser>> caplist, final String semNumber) {
        double sumCapMcs = 0;
        int sumMcs = 0;
        for (String key : caplist.keySet()) {
            if (key.equals(semNumber)) {
                for (int i = 0; i < caplist.get(key).size(); i++) {
                    double score =
                            new ConvertGradeToScoreCommand().converter(caplist.get(key).get(i).grade);
                    if (score != DONT_COUNT_SCORE) {
                        sumCapMcs += caplist.get(key).get(i).moduleCredit
                                * score;
                        sumMcs += caplist.get(key).get(i).moduleCredit;
                    }
                }
            }
        }
        double cap = sumCapMcs / sumMcs;
        return cap;
    }
}
