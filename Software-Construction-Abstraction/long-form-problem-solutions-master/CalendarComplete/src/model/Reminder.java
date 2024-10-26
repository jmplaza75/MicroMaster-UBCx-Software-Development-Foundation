package model;

public class Reminder extends Entry {

    private String note;

    //REQUIRES: date, time, label != null, date is in the future
    public Reminder(Date date, Time time, String label){
        super(date, time, label);
    }

    //EFFECTS: if note is not null, return note. Otherwise return "No note added"
    public String getNote(){
        if(note == null){
            return "No note added";
        } else {
            return note;
        }
    }

    //MODIFIES: this
    //EFFECTS: sets note to given note
    public void setNote(String note){
        this.note = note;
    }
}
