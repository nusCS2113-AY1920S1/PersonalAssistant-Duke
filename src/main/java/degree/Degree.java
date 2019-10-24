package degree;


import exception.DukeException;
import module.ConjunctiveModule;
import module.Module;
import module.ModuleList;
import module.NonDescriptive;
import parser.Parser;

import java.lang.reflect.InvocationTargetException;
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
    private List<String> aliases = new ArrayList<>();
    //private DisjointUnionSet aliases;
    private Integer uem = null;

    /**
     * Creates the degree using the scraped information in the csv file
     * @param input is a list of strings which should be taken from storage
     */
    public Degree(List<String> input) throws DukeException {
        validateList(input);
        for (int row = 1; row< input.size(); row++)
        {
            String[] split = input.get(row).split(",", -1);
            assert(split.length == 12);
            int col = 0;
            setUem(split[col]);
            for(col = 1; col < split.length - 1; col += 2)
            {
                //System.out.println("Trying to add " + col +": "+split[col]+" "+split[col+1]);
                addToList(col, split[col], split[col+1]);
            }
            assert (col == split.length - 1);
            addAlias(split[col]);
        }
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
            String[] split = in.split(" OR ", -1);
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
        printList(this.facultyReq);
        printListHeader("Common Core Requirements:", this.commonCore.getSum());
        printList(this.commonCore);
        printListHeader("Core Modules:", this.coreMod.getSum());
        printList(this.coreMod);
        printListHeader("Design and Project Modules:", this.designProject.getSum());
        printList(this.designProject);
        printListHeader("Electives:", this.electives.getSum());
        printList(this.electives);
        printListHeader("Total", 160);
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
     * Given a list of modules, sort them, and then print out the name and mcs linked to each modules
     *
     * @param modList is a ModuleList class which contains a list of modules;
     */
    private void printList(ModuleList modList)
    {
        List<Module> temp = new ArrayList<>(modList.getModules());
        Collections.sort(temp);
        for(Module res: temp)
        {
            res.print();
        }
    }
}
