package javafx;

import javafx.beans.property.SimpleStringProperty;

public class DegreesFX {
    private final SimpleStringProperty number = new SimpleStringProperty("");
    private final SimpleStringProperty moduleCode = new SimpleStringProperty("");
    private final SimpleStringProperty moduleName = new SimpleStringProperty("");
    private final SimpleStringProperty moduleCredit = new SimpleStringProperty("");

    public DegreesFX() {
        this("", "", "", "");
    }

    public DegreesFX(String number, String moduleCode, String moduleName, String moduleCredit) {
        setNumber(number);
        setModuleCode(moduleCode);
        setModuleName(moduleName);
        setModuleCredit(moduleCredit);
    }

    public String getNumber() {
        return number.get();
    }

    public void setNumber(String input) {
        number.set(input);
    }

    public String getModuleCode() {
        return moduleCode.get();
    }

    public void setModuleCode(String input) {
        moduleCode.set(input);
    }

    public String getModuleCredit() {
        return moduleCredit.get();
    }

    public void setModuleCredit(String input) {
        moduleCredit.set(input);
    }

    public String getModuleName() {
        return moduleName.get();
    }

    public void setModuleName(String input) {
        moduleName.set(input);
    }


}
