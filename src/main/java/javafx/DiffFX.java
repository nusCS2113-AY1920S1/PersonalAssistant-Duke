package javafx;

import javafx.beans.property.SimpleStringProperty;


/**
 * javafx class to display degree data in a tabular format in the GUI.
 * Each row will have 2 modules being compared against each other.
 * Each module will display its module code, name and credits, for a total of 6 columns for each DiffFX object.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.3
 */
public class DiffFX {
    private final SimpleStringProperty moduleCode1 = new SimpleStringProperty("");
    private final SimpleStringProperty moduleName1 = new SimpleStringProperty("");
    private final SimpleStringProperty moduleCredit1 = new SimpleStringProperty("");
    private final SimpleStringProperty moduleCode2 = new SimpleStringProperty("");
    private final SimpleStringProperty moduleName2 = new SimpleStringProperty("");
    private final SimpleStringProperty moduleCredit2 = new SimpleStringProperty("");

    /**
     * Empty constructor.
     */
    public DiffFX() {
        this("", "", "", "", "", "");
    }

    /**
     * Constructor for this DiffFX object.
     * It takes in 3 parameters from 2 modules at the same time, and displays both side by side.
     *
     * @param moduleCode1 The module code of the first module.
     * @param moduleName1 The name of the first module.
     * @param moduleCredit1 The number of credits the first module has.
     * @param moduleCode2 The module code of the second module.
     * @param moduleName2 The name of the second module.
     * @param moduleCredit2 The number of credits the second module has.
     */
    public DiffFX(String moduleCode1, String moduleName1, String moduleCredit1,
                  String moduleCode2, String moduleName2, String moduleCredit2) {
        setModuleCode1(moduleCode1);
        setModuleName1(moduleName1);
        setModuleCredit1(moduleCredit1);
        setModuleCode2(moduleCode2);
        setModuleName2(moduleName2);
        setModuleCredit2(moduleCredit2);
    }

    /**
     * Returns the module code of the first module.
     * Required to be used by tableview.
     *
     * @return The module code of the module as a string.
     */
    public String getModuleCode1() {
        return moduleCode1.get();
    }

    /**
     * Sets the module code of this first module in this DegreesFX object
     *
     * @param input The module code of the first module as a string, used in the constructor
     */
    public void setModuleCode1(String input) {
        moduleCode1.set(input);
    }

    /**
     * Returns the modular credits of the first module.
     * Required to be used by tableview.
     *
     * @return The modular credits of the first module as a string.
     */
    public String getModuleCredit1() {
        return moduleCredit1.get();
    }

    /**
     * Sets the modular credit of the first module of this DegreesFX object.
     *
     * @param input The modular credit of the first module as a string, used in the constructor.
     */
    public void setModuleCredit1(String input) {
        moduleCredit1.set(input);
    }

    /**
     * Returns the name of the first module.
     * Required to be used by tableview.
     *
     * @return The name of the first module as a string.
     */
    public String getModuleName1() {
        return moduleName1.get();
    }

    /**
     * Sets the name of the first module of this DegreesFX object.
     *
     * @param input The name of the first module as a string, used in the constructor.
     */
    public void setModuleName1(String input) {
        moduleName1.set(input);
    }

    /**
     * Returns the module code of the second module.
     * Required to be used by tableview.
     *
     * @return The module code of the second module as a string.
     */
    public String getModuleCode2() {
        return moduleCode2.get();
    }

    /**
     * Sets the module code of this second module in this DegreesFX object
     *
     * @param input The module code of the second module as a string, used in the constructor.
     */
    public void setModuleCode2(String input) {
        moduleCode2.set(input);
    }

    /**
     * Returns the modular credits of the second module.
     * Required to be used by tableview.
     *
     * @return The modular credits of the second module as a string.
     */
    public String getModuleCredit2() {
        return moduleCredit2.get();
    }

    /**
     * Sets the modular credit of the second module of this DegreesFX object
     *
     * @param input The modular credit of the second module as a string, used in the constructor.
     */
    public void setModuleCredit2(String input) {
        moduleCredit2.set(input);
    }

    /**
     * Returns the name of the second module.
     * Required to be used by tableview.
     *
     * @return The name of the second module as a string.
     */
    public String getModuleName2() {
        return moduleName2.get();
    }

    /**
     * Sets the name of the second module of this DegreesFX object
     *
     * @param input The name of the second module as a string, used in the constructor.
     */
    public void setModuleName2(String input) {
        moduleName2.set(input);
    }


}
