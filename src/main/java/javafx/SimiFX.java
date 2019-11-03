package javafx;

import javafx.beans.property.SimpleStringProperty;


/**
 * javafx class to display degree data in a tabular format in the GUI.
 * Each module will display its module code, name and credits, for only 3 columns in a SimiFX object
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.3
 */
public class SimiFX {
    private final SimpleStringProperty moduleCode = new SimpleStringProperty("");
    private final SimpleStringProperty moduleName = new SimpleStringProperty("");
    private final SimpleStringProperty moduleCredit = new SimpleStringProperty("");

    /**
     * Empty constructor.
     */
    public SimiFX() {
        this("", "", "");
    }

    /**
     * Constructor for this SimiFX object.
     * It takes in 3 parameters from 1 module that is common between both modules being compared.
     *
     * @param moduleCode The module code of the first module.
     * @param moduleName The name of the first module.
     * @param moduleCredit The number of credits the first module has.
     */
    public SimiFX(String moduleCode, String moduleName, String moduleCredit) {
        setModuleCode(moduleCode);
        setModuleName(moduleName);
        setModuleCredit(moduleCredit);
    }

    /**
     * Returns the module code of the first module.
     * Required to be used by tableview.
     *
     * @return The module code of the module as a string.
     */
    public String getModuleCode() {
        return moduleCode.get();
    }

    /**
     * Sets the module code of this first module in this DegreesFX object
     *
     * @param input The module code of the first module as a string, used in the constructor
     */
    public void setModuleCode(String input) {
        moduleCode.set(input);
    }

    /**
     * Returns the modular credits of the module.
     * Required to be used by tableview.
     *
     * @return The modular credits of the module as a string.
     */
    public String getModuleCredit() {
        return moduleCredit.get();
    }

    /**
     * Sets the modular credit of the module of this DegreesFX object.
     *
     * @param input The modular credit of the module as a string, used in the constructor.
     */
    public void setModuleCredit(String input) {
        moduleCredit.set(input);
    }

    /**
     * Returns the name of the first module.
     * Required to be used by tableview.
     *
     * @return The name of the first module as a string.
     */
    public String getModuleName() {
        return moduleName.get();
    }

    /**
     * Sets the name of the module of this DegreesFX object.
     *
     * @param input The name of the module as a string, used in the constructor.
     */
    public void setModuleName(String input) {
        moduleName.set(input);
    }

}
