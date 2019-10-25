package rims.exception;

public class RimsException extends Exception {
    protected String tab = "\t";
    protected String hash = "********************************************************************************************************************************************";
    protected String error;

    public RimsException(String error) {
        this.error = error;
    }

    public void displayError() {
        System.out.println(tab + hash);
        System.out.println(tab + "ERROR: " + error);
        System.out.println(tab + hash);
    }
}