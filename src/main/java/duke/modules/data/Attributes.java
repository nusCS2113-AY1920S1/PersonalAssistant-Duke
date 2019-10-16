package duke.modules.data;

public class Attributes {

    private boolean su = false;
    private boolean sfs = false;
    private boolean ssgf = false;
    private boolean fyp = false;
    private boolean year = false;
    private boolean coreMod = false;
    private boolean geMod = false;
    private boolean ueMod = false;

    public Attributes() {

    }

    public boolean isSu() {
        return su;
    }

    public boolean isFyp() {
        return fyp;
    }

    public boolean isCoreMod() { return coreMod; }

    public boolean isGeMod() { return geMod; }

    public boolean isUeMod() { return ueMod; }

    @Override
    public String toString() {
        return "SU:" + ((su) ? "True" : "False");
    }
}
