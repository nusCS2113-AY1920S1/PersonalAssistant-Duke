package Tasks;

public class Timebound extends Task {
    public String period ;

    public Timebound (String description, String period) {
        super(description);
        this.period = period;
    }
    @Override
    public String toString() {
<<<<<<< HEAD
        return "P"+ "|" + super.getStatusIcon() + "| " + super.description + "|" + period;
=======
        return "P"+ " | " + super.getStatusIcon() + " | " + super.description + " | " + period;
>>>>>>> f4d56ffa7287b3411536cd5032d0656f1f1e0185
    }

    @Override
    public String listformat(){
<<<<<<< HEAD
        return "[P]" + "[" + super.getStatusIcon() + "]" + super.description + "(between:" + period + ")" ;
    }

=======
        return "[P]" + "[" + super.getStatusIcon() + "] " + super.description + " (between: " + period + ")" ;
    }


>>>>>>> f4d56ffa7287b3411536cd5032d0656f1f1e0185
}
