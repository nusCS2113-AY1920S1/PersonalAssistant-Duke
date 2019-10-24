package gazeeebo.commands.capCalculator;

import java.util.ArrayList;
import java.util.Map;

public class CalculateCAPCommand {
    /**
     * Calculate the CAP of all the modules.
     *
     * @param CAPList the object that deals stores semNumber, moduleCode, moduleCredits and GPA score.
     * @return the GPA.
     */
    public double CAPCalculator(Map<String, ArrayList<CAPCommand>> CAPList) {
        double sumGPAMCS = 0;
        int sumMCS = 0;
        for (String key : CAPList.keySet()) {
            for(int i = 0; i < CAPList.get(key).size(); i++) {
                double score = new ConvertGradeToScoreCommand().converter(CAPList.get(key).get(i).grade);
                if(score != 0.1) {
                    sumGPAMCS += CAPList.get(key).get(i).moduleCredit * score;
                    sumMCS += CAPList.get(key).get(i).moduleCredit;
                }
            }
        }
        double cap = sumGPAMCS / sumMCS;
        return cap;
    }

    /**
     * Calculate the GPA of the particular sem.
     *
     * @param CAPList   the object that deals stores semNumber, moduleCode, moduleCredits and GPA.
     * @param semNumber the sem which you want to find the GPA.
     * @return the GPA.
     */
    public double CAPCalculatorPerSem(final Map<String, ArrayList<CAPCommand>> CAPList, final String semNumber) {
        double sumGPAMCS = 0;
        int sumMCS = 0;
        for (String key : CAPList.keySet()) {
            if (key.equals(semNumber)) {
                for(int i = 0; i < CAPList.get(key).size(); i++) {
                    double score = new ConvertGradeToScoreCommand().converter(CAPList.get(key).get(i).grade);
                    if(score != 0.1) {
                        sumGPAMCS += CAPList.get(key).get(i).moduleCredit * score;
                        sumMCS += CAPList.get(key).get(i).moduleCredit;
                    }
                }
            }
        }
        double cap = sumGPAMCS / sumMCS;
        return cap;
    }
}
