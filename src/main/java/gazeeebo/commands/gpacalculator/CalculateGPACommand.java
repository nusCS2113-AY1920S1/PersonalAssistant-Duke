package gazeeebo.commands.gpacalculator;

import java.util.Map;

public class CalculateGPACommand {
    public double GPACalculator(Map<String, GPACommand> GPAList) {
        double cap;
        double sum_GPA_MCS = 0;
        int sum_MCS = 0;
        for(String key: GPAList.keySet()) {
            sum_GPA_MCS += GPAList.get(key).moduleCredit * GPAList.get(key).score;
            sum_MCS += GPAList.get(key).moduleCredit;
        }
        cap = sum_GPA_MCS/sum_MCS;
        return cap;
    }
}
