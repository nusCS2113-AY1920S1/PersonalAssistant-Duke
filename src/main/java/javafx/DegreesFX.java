package javafx;

import javafx.beans.property.SimpleStringProperty;


/**
 * javafx class to display degree data in a tabular format in the GUI.
 * Each DegreesFX object is a module with an id, a module code, a name and number of credits.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.3
 */
public class DegreesFX {
    private final SimpleStringProperty number = new SimpleStringProperty("");
    private final SimpleStringProperty moduleCode = new SimpleStringProperty("");
    private final SimpleStringProperty moduleName = new SimpleStringProperty("");
    private final SimpleStringProperty moduleCredit = new SimpleStringProperty("");

    /**
     * Empty constructor.
     */
    public DegreesFX() {
        this("", "", "", "");
    }

    /**
     * Constructor method for module data.
     * Takes in important data to be displayed in the table view degreesView.
     *
     * @param number The numerical ID of the module.
     * @param moduleCode The module code.
     * @param moduleName The name of the module.
     * @param moduleCredit The number of modular credits this module is worth as a string.
     */
    public DegreesFX(String number, String moduleCode, String moduleName, String moduleCredit) {
        setNumber(number);
        setModuleCode(moduleCode);
        setModuleName(moduleName);
        setModuleCredit(moduleCredit);
    }

    /**
     * Returns the numerical ID of the module.
     * Required to be used by tableview.
     *
     * @return The numerical ID of the module as a string.
     */
    public String getNumber() {
        return number.get();
    }

    /**
     * Sets the numerical ID of this DegreesFX object
     *
     * @param input The numerical ID of this module as a string, used in the constructor
     */
    public void setNumber(String input) {
        number.set(input);
    }

    /**
     * Returns the module code of the module.
     * Required to be used by tableview.
     *
     * @return The module code of the module as a string.
     */
    public String getModuleCode() {
        return moduleCode.get();
    }

    /**
     * Sets the module code of this DegreesFX object
     *
     * @param input The module code of this module as a string, used in the constructor
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
     * Sets the modular credit of this DegreesFX object
     *
     * @param input The modular credit of this module as a string, used in the constructor
     */
    public void setModuleCredit(String input) {
        moduleCredit.set(input);
    }

    /**
     * Returns the name of the module.
     * Required to be used by tableview.
     *
     * @return The name of the module as a string.
     */
    public String getModuleName() {
        return moduleName.get();
    }

    /**
     * Sets the name of this DegreesFX object
     *
     * @param input The name of this module as a string, used in the constructor
     */
    public void setModuleName(String input) {
        moduleName.set(input);
    }


}
