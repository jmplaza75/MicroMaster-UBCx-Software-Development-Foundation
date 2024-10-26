package model;

public abstract class Entry {

    private Date date;
    private Time time;
    private String label;
    private int intervalOfRepetition;

    //REQUIRES: date, time, label != null, date is in the future
    public Entry(Date date, Time time, String label){
        this.date = date;
        this.time = time;
        this.label = label;
    }

    //getters
    public String getLabel(){
        return label;
    }

    public Date getDate(){
        return date;
    }

    public Time getTime(){
        return time;
    }

    public boolean isRepeating(){
        return intervalOfRepetition != 0;
    }

    //EFFECTS: returns interval of repetition, or 0 if none
    public int getIntervalOfRepetition() { return intervalOfRepetition; }

    //REQUIRES: interval >= 0
    //MODIFIES: this
    //REQUIRES: interval of repetition in days, or 0 if not repeating
    public void setRepeating(int interval){
        this.intervalOfRepetition = interval;
    }

}
