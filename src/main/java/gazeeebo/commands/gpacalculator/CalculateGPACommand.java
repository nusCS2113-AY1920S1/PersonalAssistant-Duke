package gazeeebo.commands.gpacalculator;

import java.util.ArrayList;
import java.util.Map;

public class CalculateGPACommand {
    /**
     * Calculate the GPA of all the modules.
     *
     * @param gpalist the object that deals stores semNumber, moduleCode, moduleCredits and GPA score.
     * @return the GPA.
     */
    public double GPACalculator(Map<String, ArrayList<GPACommand>> gpalist) {
        double cap;
        double sumGPAMCS = 0;
        int sumMCS = 0;
        for (String key : gpalist.keySet()) {
            for(int i = 0; i < gpalist.get(key).size(); i++) {
                sumGPAMCS += gpalist.get(key).get(i).moduleCredit * gpalist.get(key).get(i).score;
                sumMCS += gpalist.get(key).get(i).moduleCredit;
            }
        }
        cap = sumGPAMCS / sumMCS;
        return cap;
    }

    /**
     * Calculate the GPA of the particular sem.
     *
     * @param gpalist   the object that deals stores semNumber, moduleCode, moduleCredits and GPA.
     * @param semNumber the sem which you want to find the GPA.
     * @return the GPA.
     */
    public double gpacalculatorpersem(final Map<String, ArrayList<GPACommand>> gpalist, final String semNumber) {
        double cap;
        double sumGPAMCS = 0;
        int sumMCS = 0;
        for (String key : gpalist.keySet()) {
            if (key.equals(semNumber)) {
                for(int i = 0; i < gpalist.get(key).size(); i++) {
                    sumGPAMCS += gpalist.get(key).get(i).moduleCredit * gpalist.get(key).get(i).score;
                    sumMCS += gpalist.get(key).get(i).moduleCredit;
                }
            }
        }
        cap = sumGPAMCS / sumMCS;
        return cap;
    }
}
