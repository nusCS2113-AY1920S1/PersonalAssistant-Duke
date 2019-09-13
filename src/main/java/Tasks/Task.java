package Tasks;

abstract public class Task {
    public String description;
    public boolean isDone;
    public String symbol;

    public Task(String description){
        this.description=description;
        this.isDone=false;
    }

    public String getStatusIcon(){
        return (isDone?"\u2713":"\u2718");
    }

    public void markAsDone(){
        isDone=true;
    }

    public String getSymbol() {
        return symbol;
    }


}
