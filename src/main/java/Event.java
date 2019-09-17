class Event extends Task{
    private String by;
    private Date date;
    private Time time;
    public Event(String name, String by) {
        super(name);
        this.by = by;

        String arr[] = by.split(" ", 3);
        date = new Date(arr[1]);
        time = new Time(arr[2]);
    }

    @Override
    public String getTime(){
        return this.by;
    }

    @Override
    public String toString(){
        return "[E] " + super.toString() + " (by:" + date.toString() + " " +  time.toString()  + ")";
    }

    @Override
    public String saveForm(){
        return "[E]:"+ super.tick() + ":" + super.getName() + ":" +  by;
    }
}
