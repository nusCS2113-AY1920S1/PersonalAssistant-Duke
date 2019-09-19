class Task {
    protected boolean is_done;
    protected String name;

    public Task(String name){
        this.name = name;
        this.is_done = false;
    }

    public String tick(){
        if (is_done) return "[V]";
        else return "[X]";
    }

    public void have_done()
    {
        this.is_done = true;
    }

    public boolean done()
    {
        return this.is_done;
    }

    public String getName() {
        return this.name;
    }

    public String getTime(){
        return null;
    }

    @Override
    public String toString(){
        return this.tick() + " " +  name;
    }

    public String saveForm(){
        return name;
    }
}
