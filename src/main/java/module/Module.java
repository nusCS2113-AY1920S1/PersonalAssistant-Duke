package module;

import parser.Parser;

import java.util.Arrays;

/**
 * Basic Module class which expects a Module Code of type [A-Z]{2,3}[1-9]([0-9]{3}|X{3})[A-Z]{0,1}
 * Example CS2113T, CS2101, MSE1102
 */
public class Module implements Comparable<Module> {
    String code = "";
    private String name = "";
    Integer mc = 0;

    /**
     * Default module constructor
     */
    Module() {
    }

    /**
     * Normal module constructor accepts 2 strings which indicate the code and the name
     *
     * @param code String is the [A-Z]{2,3}[1-9]([0-9]{3}|X{3})[A-Z]{0,1} code
     * @param name String is the official name of the modules
     * @param mc is the credit amount of a module
     */
    public Module(String code, String name, Integer mc)
    {
        this.code = code;
        this.name = name;
        this.mc = mc;
    }

    /**
     * Outputs the user friendly view for people to know which module is it
     * Follows default Parser screen width
     *
     * @return String containing the module's code then its human friendly name, separated by a space
     */
    public String getPrint()
    {
        StringBuilder module = new StringBuilder();
        module.append(getCode());
        module.append(" ");
        module.append(getName());
        if(module.length() > Parser.windowWidth - 4) {
            module.setLength(Parser.windowWidth - 8);
            module.append("... ");
        }
        else {
            char[] pad = new char[Math.max(Parser.windowWidth - module.length() - 4, 0)];
            Arrays.fill(pad, ' ');
            module.append(pad);
        }
        module.append(getMc());
        while(module.length() < Parser.windowWidth)
        {
            module.append(" ");
        }
        return module.toString();
    }

    /**
     * Gets the string to be printed with a fixed width
     *
     * @param setWidth the width the string should be fixed to
     * @return the String to be printed
     */
    public String getPrint(int setWidth) {
        StringBuilder module = new StringBuilder();
        module.append(getCode());
        module.append(" ");
        module.append(getName());
        if(module.length() > setWidth - 5) {
            module.setLength(setWidth - 9);
            module.append("...  ");
        }
        else {
            char[] pad = new char[Math.max(setWidth - module.length() - 4, 0)];
            Arrays.fill(pad, ' ');
            module.append(pad);
        }
        module.append(getMc());
        while(module.length() < setWidth)
        {
            module.append(" ");
        }
        return module.toString();
    }

    public void print()
    {
        System.out.println(getPrint());
    }


    /**
     * Returns the module's Name for the side tab
     *
     * @return String which is the module name
     */
    public String tabModuleName() {
        return this.getName();
    }

    /**
     * Returns the module's code
     *
     * @return string which is the module's code
     */
    public String tabModuleCode() {
        return this.getCode();
    }

    /**
     * Returns the code of the module
     *
     * @return String that is the module's code
     */
    public String getCode(){
        return this.code;
    }

    /**
     * Returns the name of a module
     *
     * @return String that is the human friendly name of the module
     */
    private String getName(){
        return this.name;
    }

    /**
     * Returns the credits allocated to the module
     *
     * @return Integer that is the credits allocated to the module
     */
    Integer getMc()
    {
        return mc;
    }

    /**
     * Returns the comparable version of this module
     *
     * @return String which contains the module's code
     */
    @Override
    public String toString() {
        return this.getCode();
    }

    /**
     * Compares between another object (preferably module) to determine if they are equivalent
     * Criteria of comparison is preferably their code names
     * In the case of NonDescriptive Module, their code and mc value
     *
     * @param obj is the object to be compared
     * @return true if it is a match (allowing conjunctive modules to be equivalent to a singular module)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Module)
            return this.toString().equals(obj.toString());
        else
            return false;
    }

    /**
     * Compare 2 modules based on their module code
     *
     * @param other is the other module to be compared to
     * @return A negative integer, zero, or a positive integer as this module
     *          is less than, equal to, or greater than the supplied module object.
     *
     */
    @Override
    public int compareTo(Module other)
    {
        return this.getCode().compareTo(other.getCode());
    }
}
