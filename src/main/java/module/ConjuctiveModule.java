package module;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Conjunctive Modules indicate that they are Modules of a standard type which can be used to complement each other
 * EG CEG Requirements: CG3207 OR CS3230
 * Explanation: Fulfilling either of these 2 counts towards "fulfilling both"
 */
public class ConjuctiveModule extends Module {
    private Map<String, String> modules = new TreeMap<>();

    /**
     * Conjuctive Modules are those linked together as either or requirements
     * Fulfilling any one of them is equivalent to fulfilling the MCs allocated to them
     *
     * @param input String of modules separated by " OR "
     * @param mc is the credit amount of a module
     */
    public ConjuctiveModule(String input, Integer mc)
    {
        this.mc = mc;
        String[] split = input.split("\\sOR\\s");
        for(String full: split)
        {
            Scanner temp = new Scanner(full);
            String code = temp.next();
            String name = temp.nextLine();
            name = name.strip();
            modules.put(code, name);
            temp.close();
        }
    }

    /**
     * Returns the codes tagged to the conjunctive modules, separated by |
     *
     * @return String of codes separated by |
     */
    public String getCode(){
        StringBuilder list = new StringBuilder();
        for(Map.Entry<String,String> entry : modules.entrySet()) {
            String key = entry.getKey();

            list.append(key).append("|");
        }
        return list.substring(0, list.length() - 1  );
    }

    /**
     * Returns the view friendly version consisting of the module code and name separated by OR
     *
     * @return String in the fashion described above
     */
    @Override
    public String viewFriendly()
    {
        StringBuilder list = new StringBuilder();
        for(Map.Entry<String,String> entry : modules.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            list.append(key).append(" ").append(value).append(" OR ");
        }
        return list.substring(0, list.length() - 4);
    }


}
