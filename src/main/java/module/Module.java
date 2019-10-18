package module;

/**
 * Basic Module class which expects a Module Code of type [A-Z]{2,3}[1-9]([0-9]{3}|X{3})[A-Z]{0,1}
 * Example CS2113T, CS2101, MSE1102
 */
public class Module {
    String code;
    private String name;
    Integer mc;

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
     *
     * @return String containing the module's code then its human friendly name, separated by a space
     */
    public String viewFriendly()
    {
        return this.getCode() + " " + this.getName();
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
    public String getName(){
        return this.name;
    }

    /**
     * Returns the credits allocated to the module
     *
     * @return Integer that is the credits allocated to the module
     */
    public Integer getMc()
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
            return this.toString().matches(obj.toString());
        else
            return false;
    }
}
