package duke.modules.data;

public class Attributes {

    private boolean su = false;
    private boolean sfs = false;
    private boolean ssgf = false;
    private boolean fyp = false;
    private boolean year = false;

    public Attributes() {

    }

    public boolean isSu() {
        return su;
    }

    public boolean isFyp() {
        return fyp;
    }

    @Override
    public String toString() {
        return "SU:" + ((su) ? "True" : "False");
    }
}
