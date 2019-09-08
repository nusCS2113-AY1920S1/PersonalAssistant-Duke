public class Task {
    protected String description;
    protected boolean isDone;
    protected String symbol;

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

    String getSymbol() {
        return symbol;
    }


}
