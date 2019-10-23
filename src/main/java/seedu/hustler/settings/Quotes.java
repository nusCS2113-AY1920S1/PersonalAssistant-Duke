package seedu.hustler.settings;

public class Quotes extends Settings{

    private static Boolean lock;

    private String quoteDescription;



    public Quotes(String quoteDescription) {

        this.quoteDescription = quoteDescription;
        lock = true;

    }

    @Override
    public void set() {

    }

    public static Boolean unlock() {
        return lock = false;
    }





}
