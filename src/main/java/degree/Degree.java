package degree;


import exception.DukeException;
import module.ConjuctiveModule;
import module.Module;
import module.ModuleList;
import module.NonDescriptive;
import parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    //private DisjoinUnionSet ;
    private Integer uem = null;

    /**
     * Creates the degree using the scraped information in the csv file
     * @param input is a list of strings which should be taken from storage
     */
    public Degree(List<String> input) throws DukeException {
        for (int row = 1; row< input.size(); row++)
        {
            String[] split = input.get(row).split(",");
            assert(split.length == 12);
            int col = 0;
            setUem(split[col]);
            for(col = 1; col < split.length - 1; col += 2)
            {
                addToList(col, split[col], split[col+1]);
            }
            assert (col == split.length - 1);
            addAlias(split[col+1]);
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
            String[] split = in.split(" OR ");
            validateModule(split);
            return new ConjuctiveModule(in, credits);
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
}
