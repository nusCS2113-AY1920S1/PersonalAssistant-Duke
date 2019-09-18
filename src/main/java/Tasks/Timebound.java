package Tasks;
public class Timebound extends Task {
    public String period ;

    public Timebound (String description, String period) {
        super(description);
        this.period = period;
    }
    @Override
    public String toString() {
        return "P"+ " | " + super.getStatusIcon() + " | " + super.description + " | " + period;
    }
    @Override
    public String listformat(){
        return "[P]" + "[" + super.getStatusIcon() + "] " + super.description + " (between: " + period + ")" ;
    }

}
