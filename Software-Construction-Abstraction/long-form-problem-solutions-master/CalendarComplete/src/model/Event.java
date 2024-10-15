package model;

public class Event extends Entry {

    private Reminder reminder;

    //REQUIRES: date, time, label != null, date is in the future
    public Event(Date date, Time time, String label){
        super(date, time, label);
    }

    //getters
    public Reminder getReminder(){
        return reminder;
    }

    //REQUIRES: r != null
    //MODIFIES: this
    //EFFECTS: sets this event's reminder to r
    public void setReminder(Reminder r){
        this.reminder = r;
    }
}
