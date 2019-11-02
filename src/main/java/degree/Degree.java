package degree;


import exception.DukeException;
import module.ConjunctiveModule;
import module.Module;
import module.ModuleList;
import module.NonDescriptive;
import parser.Parser;

import java.util.*;

/**
 * Degree Class contains the module information for the Degree
 * It is split into 5 Lists: Common Core, Core Module, Faculty Requirements, Design & Projects, Electives
 * It contains information about the number of modular credits allocated to Unrestricted Elective Modules
 * It also contains information about the Common Aliases
 *
 */
public class Degree {
    private ModuleList commonCore = new ModuleList();
    private ModuleList coreMod = new ModuleList();
    private ModuleList facultyReq = new ModuleList();
    private ModuleList designProject = new ModuleList();
    private ModuleList electives = new ModuleList();
    private ModuleList master = new ModuleList();
    private List<String> aliases = new ArrayList<>();
    //private DisjointUnionSet aliases;
    private Integer uem = null;

    /**
     * Creates the degree using the scraped information in the csv file
     * @param input is a list of strings which should be taken from storage
     */
    Degree(List<String> input) throws DukeException {
        validateList(input);
        for (int row = 1; row< input.size(); row++)
        {
            String[] split = input.get(row).split(",", -1);
            assert(split.length == 12);
            int col = 0;
            setUem(split[col].strip());
            for(col = 1; col < split.length - 1; col += 2)
            {
                //System.out.println("Trying to add " + col +": "+split[col]+" "+split[col+1]);
                addToList(col, split[col].strip(), split[col+1].strip());
            }
            assert (col == split.length - 1);
            addAlias(split[col].strip());
        }
        if(uem == null)
            setUem("0");
        if(this.aliases.isEmpty())
        {
            throw new DukeException("There should be at least one common alias to refer to the degree");
        }
        master.add(commonCore);
        master.add(coreMod);
        master.add(facultyReq);
        master.add(designProject);
        master.add(electives);
    }

    /**
     * Adds aliases to this object's list of aliases
     *
     * @param input is the string to be added, nothing will be added if it is blank
     */
    private void addAlias(String input)
    {
        if(!input.isBlank())
            this.aliases.add(input);
    }

    /**
     * Adds to the ModuleLists in degree based on which column (subsection) it belongs to
     *
     * @param list is the integer indicating the list to be added, should only be 1,3,5,7,9
     * @param module is the module code and name
     * @param mcs is the credit amount of the module
     * @throws DukeException is thrown when any steps of the validation or adding fails;
     */
    private void addToList(Integer list, String module, String mcs) throws DukeException
    {
        try{
            if(module.isBlank() && mcs.isBlank())
                return;
            if(module.isBlank() ^ mcs.isBlank())
                throw new DukeException("Both module name and credits must be filled");
            Module mod = createNewModule(module, mcs);
            switch(list) {
            case 1:
                this.facultyReq.add(mod);
                return;
            case 3:
                this.commonCore.add(mod);
                return;
            case 5:
                this.coreMod.add(mod);
                return;
            case 7:
                this.designProject.add(mod);
                return;
            case 9:
                this.electives.add(mod);
                return;
            default:
                throw new DukeException("Invalid addition to list");
            }
        } catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        }

    }


    /**
     * Create a New Module to be added into one of various lists
     *
     * @param in is a String containing module(s) data
     * @param mcs is a String which should be a non-negative integer
     * @return Module object which contains the module data
     * @throws DukeException is thrown when invalid module format is given
     */
    private Module createNewModule(String in, String mcs) throws DukeException {
        int credits = validInt(mcs);
        if(in.contains(" OR "))
        {
            String[] split = in.split("\\s+OR\\s+", -1);
            validateModule(split);
            return new ConjunctiveModule(in, credits);
        }
        if(validateModule(in)) {
            Scanner splitter = new Scanner(in);
            String code = splitter.next();
            String name = splitter.nextLine();
            name = name.strip();
            return new Module(code, name, credits);
        }
        return new NonDescriptive(in, credits);
    }

    /**
     * Checks if the string given to it is a valid module code
     *
     * @param input is a String which is the module name
     * @return true if it follows the specific module format, otherwise false
     * @throws DukeException is thrown when the module name does not match specifications
     */
    private boolean validateModule(String input) throws DukeException
    {
        Scanner checker = new Scanner(input);
        String code = checker.next();
        if(!checker.hasNextLine())
        {
            return false;
        }
        String name = checker.nextLine();
        name = name.strip();
        if(code.matches(Parser.moduleFormat) && name.isBlank())
            throw new DukeException("Module must have a valid name");
        return code.matches(Parser.moduleFormat) && !name.isBlank();
    }

    /**
     * Checks if the string arrays elements given to it are valid module codes
     *
     * @param input is a String which is the module name
     * @throws DukeException is thrown when the module name does not match specifications
     */
    private void validateModule(String[] input) throws DukeException
    {
        try
        {
            for(String module: input)
            {
                if(!validateModule(module))
                    throw new DukeException("Invalid Module Code for Conjunctive modules");
            }
        } catch (DukeException e){
            throw new DukeException(e.getLocalizedMessage());
       }
    }

    /**
     * Sets the Unrestriced Elective Modules credit amount
     *
     * @param in String which is a non negative integer
     * @throws DukeException when a negative integer or invalid string is passed
     */
    private void setUem(String in) throws DukeException {
        try {
            if(in.isBlank())
                return;
            if(this.uem != null)
                throw new DukeException("Unrestricted Elective Modules Amount already has a value set.");
            if(!in.isBlank())
                this.uem = validInt(in);
        } catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new DukeException("That is NOT a valid integer");
        }
    }

    /**
     * Determines if a string to be converted is a non negative int
     *
     * @param in is a String input which should be a valid non negative integer
     * @throws DukeException when a negative integer or invalid string is passed
     */
    private int validInt(String in) throws DukeException {
        try {
            int amount = Integer.parseInt(in);
            if(amount < 0)
                throw new DukeException("Non Negative Integer expected.");
            return amount;
        } catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new DukeException("That is NOT a valid integer");
        }
    }

    /**
     * Validates the csv files split by newlines
     *
     * @param input is list of lines from csv file
     * @throws DukeException when format of csv is incorrect
     */
    private void validateList(List<String> input) throws DukeException {
        if(input == null)
            throw new DukeException("Degree not found");
        if(input.isEmpty())
            throw new DukeException("No data found");
        if(input.size() == 1)
            throw new DukeException("Insufficient Data");
        String[] split = input.get(1).split(",", -1);
        if(split.length != 12)
            throw new DukeException("Incorrect Number of Columns");
    }

    /**
     * Compares this degree to another degree
     *
     * @param other is the other degree class to be compared to
     */
    void compare(Degree other)
    {
        System.out.println("Comparing between " + getProperName() + " " + other.getProperName());
        System.out.println("General Education Modules (GE) (5 Modules, each of 4MCs)");
        System.out.println("Similarities:");
        System.out.println("Human and Cultures (H&C)");
        System.out.println("GER 1000 Quantitative Reasoning (QR),");
        System.out.println("Thinking and Expression (T&E)");
        System.out.println("Singapore Studies (SS)");
        System.out.println("GEQ 1000 Asking Questions (AQ))");
        System.out.println();
        printListHeader("Unrestricted Electives");
        if (this.getUem() != other.getUem()) {
            printCentralized("Differences:");
            printSideBySide(this.getUem(), other.getUem());
        }
        else
        {
            printCentralized("Similarities:");
            printCentralized(String.valueOf(this.getUem()));
        }
        System.out.println();
        System.out.println("Programme Requirements");
        printListHeader("Faculty Requirements:");
        printListDifference(this.getFacultyReq(), other.getFacultyReq());
        printListHeader("Common Core Requirements:");
        printListDifference(this.getCommonCore(), other.getCommonCore());
        printListHeader("Core Modules:");
        printListDifference(this.getCoreMod(), other.getCoreMod());
        printListHeader("Design and Project Modules:");
        printListDifference(this.getDesignProject(), other.getDesignProject());
        printListHeader("Electives:");
        printListDifference(this.getElectives(), other.getElectives());
        printListHeader("Total:");
        printCentralized("Similar:");
        printSideBySide(160, 160);
    }

    /**
     * Prints out the degree details
     *
     */
    public void print()
    {
        System.out.println("General Education Modules (GE) (5 Modules, each of 4MCs)");
        System.out.println("Human and Cultures (H&C)");
        System.out.println("GER 1000 Quantitative Reasoning (QR),");
        System.out.println("Thinking and Expression (T&E)");
        System.out.println("Singapore Studies (SS)");
        System.out.println("GEQ 1000 Asking Questions (AQ))");
        System.out.println();
        printListHeader("Unrestricted Electives", this.uem);
        System.out.println();
        System.out.println("Programme Requirements");
        printListHeader("Faculty Requirements:", this.facultyReq.getSum());
        this.facultyReq.print();
        printListHeader("Common Core Requirements:", this.commonCore.getSum());
        this.commonCore.print();
        printListHeader("Core Modules:", this.coreMod.getSum());
        this.coreMod.print();
        printListHeader("Design and Project Modules:", this.designProject.getSum());
        this.designProject.print();
        printListHeader("Electives:", this.electives.getSum());
        this.electives.print();
        printListHeader("Total", 160);
    }

    /**
     * Compares 2 ModuleList classes and prints out the similarities followed by te differences
     *
     * @param listOne is a ModuleList from the original class which called this method
     * @param listTwo is a ModuleList from the other degree to be compared with
     */
    private void printListDifference(ModuleList listOne, ModuleList listTwo) {
        ModuleList similar = listOne.getSimilar(listTwo);
        ModuleList myDiff = listOne.getDifference(listTwo);
        ModuleList theirDiff = listTwo.getDifference(listOne);
        printCentralized("Similarities:");
        if(similar.getModules().isEmpty()) {
            printCentralized("None");
        }
        else {
            similar.printCentral(Parser.windowWidth/2);
        }
        printCentralized("Differences:");
        if(myDiff.getModules().isEmpty() && theirDiff.getModules().isEmpty())
        {
            printCentralized("None");
        }
        else
        {
            Set<Module> mySet = myDiff.getModules();
            Set<Module> otherSet = theirDiff.getModules();
            char[] halfScreen = new char[Parser.windowWidth/2];
            Arrays.fill(halfScreen,' ');
            ArrayList<String> toPrint = new ArrayList<>();
            for(Module x: mySet) {
                toPrint.add(x.getPrint(Parser.windowWidth/2));
            }
            int counter = 0;
            for(Module y: otherSet) {
                StringBuilder res = new StringBuilder();
                if(counter < mySet.size()) {
                    res.append(toPrint.get(counter));
                    res.append(y.getPrint(Parser.windowWidth/2));
                    toPrint.set(counter, res.toString());
                }
                else {
                    res.append(halfScreen);
                    res.append(y.getPrint(Parser.windowWidth/2));
                    toPrint.add(res.toString());
                }
                counter += 1;
            }
            if(mySet.size() > otherSet.size()) {
                int diff = otherSet.size();
                while(diff < mySet.size()) {
                    String res = toPrint.get(diff)
                            + String.valueOf(halfScreen);
                    toPrint.set(diff, res);
                    diff += 1;
                }
            }
            for(String comparison: toPrint)
            {
                System.out.println(comparison);
            }
        }
        if(myDiff.getSum() != theirDiff.getSum())
        {
            printCentralized("Subtotal is DIFFERENT:");
            printSideBySide(myDiff.getSum() + similar.getSum(), theirDiff.getSum() + similar.getSum());
        }
        else
        {
            printCentralized("Subtotal is SAME:");
            printCentralized(String.valueOf(myDiff.getSum() + similar.getSum()));
        }
    }

    /**
     * Given a string and an integer, it will print out the list header (string) and its total Mc value (int)
     *
     * @param front is the header to be printed
     * @param sum is the value associated with that header
     */
    private void printListHeader(String front, int sum)
    {
        StringBuilder result = new StringBuilder();
        result.append(front);
        char[] pad = new char[Math.max(Parser.windowWidth - result.length() - 4, 0)];
        Arrays.fill(pad, ' ');
        result.append(pad);
        result.append(sum);
        System.out.println(result.toString());
    }

    /**
     * Given a string, it will print out the list header (string)
     *
     * @param front is the header to be printed
     */
    private void printListHeader(String front)
    {
        StringBuilder result = new StringBuilder();
        result.append(front);
        char[] pad = new char[Math.max(Parser.windowWidth - result.length(), 0)];
        Arrays.fill(pad, ' ');
        result.append(pad);
        System.out.println(result.toString());
    }

    /**
     * prints a String centralized to the windowwidth
     *
     * @param item is the string to be printed
     */
    private void printCentralized(String item) {
        int len = item.length();
        if(len%2 == 1)
        {
            len += 1;
        }
        int padLen = (Parser.windowWidth - len)/2;
        char[] pad = new char[padLen];
        Arrays.fill(pad, ' ');
        StringBuilder line = new StringBuilder();
        line.append(pad);
        line.append(item);
        line.append(pad);
        while(line.length() < Parser.windowWidth)
        {
            line.append(" ");
        }
        System.out.println(line.toString());
    }

    /**
     * Prints out the different uem counts for each degree
     *
     * @param no1 an integer detailing the uem count of the first degree
     * @param no2 an integer detailing the uem count of the degree being compared
     */
    private void printSideBySide(int no1, int no2) {
        StringBuilder line = new StringBuilder();
        int padLen = (Parser.windowWidth - 8)/4;
        char[] pad = new char[padLen];
        Arrays.fill(pad, ' ');
        line.append(pad);
        line.append(no1);
        if(no1 < 1000)
        {
            line.append(" ");
        }
        if(no1 < 100)
        {
            line.append(" ");
        }
        if(no1 < 10)
        {
            line.append(" ");
        }
        line.append(pad);
        line.append(pad);
        line.append(no2);
        if(no2 < 1000)
        {
            line.append(" ");
        }
        if(no2 < 100)
        {
            line.append(" ");
        }
        if(no2 < 10)
        {
            line.append(" ");
        }
        line.append(pad);
        while(line.length() < Parser.windowWidth)
        {
            line.append(" ");
        }
        System.out.println(line.toString());
    }

    /**
     * Gets the uem value
     *
     * @return uem value
     */
    private int getUem() { return this.uem;}

    /**
     * Gets the longest alias in the list of aliases
     *
     * @return String which is the longest alias (thus proper)
     */
    private String getProperName() {
        String res = "";
        for(String x:aliases) {
            if(x.length() > res.length()) {
                res = x;
            }
        }
        return res;
    }

    /**
     * Returns the similar list of modules from all modules compared to another degree
     *
     * @return ModuleList containing similar modules;
     */
    public ModuleList masterSimilar(Degree other)
    {
        return this.getMaster().getSimilar(other.getMaster());
    }

    /**
     * Returns the similar list of modules from all modules compared to another degree
     *
     * @return ModuleList containing similar modules;
     */

    public ModuleList masterDifference(Degree other)
    {
        return this.getMaster().getDifference(other.getMaster());
    }

    /**
     * Gets the CommonCore ModuleList from this Degree
     *
     * @return ModuleList which is the CommonCore ModuleList from this Degree
     */
    private ModuleList getCommonCore(){ return this.commonCore; }

    /**
     * Gets the CoreMod ModuleList from this Degree
     *
     * @return ModuleList which is the CoreMod ModuleList from this Degree
     */
    private ModuleList getCoreMod(){return this.coreMod;}

    /**
     * Gets the FacultyReq ModuleList from this Degree
     *
     * @return ModuleList which is the FacultyReq ModuleList from this Degree
     */
    private ModuleList getFacultyReq() {return this.facultyReq;}

    /**
     * Gets the DesignProject ModuleList from this Degree
     *
     * @return ModuleList which is the DesignProject ModuleList from this Degree
     */
    private ModuleList getDesignProject() {return this.designProject;}

    /**
     * Gets the Electives ModuleList from this Degree
     *
     * @return ModuleList which is the Electives ModuleList from this Degree
     */
    private ModuleList getElectives() {return this.electives;}

    /**
     * Gets the master list of modules
     *
     * @return ModuleList which contains all Modules this degree has
     */
    ModuleList getMaster() {return this.master;}

    /**
     * Gets the list of aliases
     *
     * @return an arraylist of aliases
     */
    List<String> getAlias() {return this.aliases;}
}
