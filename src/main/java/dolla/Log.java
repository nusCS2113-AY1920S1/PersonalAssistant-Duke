package dolla;

/**
 * A class that is inherited from all forms of logging, ie. Entry and Debt,
 * so that we can perform polymorphism.
 */
public abstract class Log {

    public abstract String getLogText();

    public abstract String getDescription();
}
