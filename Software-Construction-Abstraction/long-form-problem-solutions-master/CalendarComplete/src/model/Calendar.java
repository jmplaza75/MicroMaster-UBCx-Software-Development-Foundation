package model;

import java.util.ArrayList;
import java.util.List;

public class Calendar {

    private String email;
    private List<Entry> entries;
    private Date currentDate;

    public Calendar(Date currentDate){
        this.currentDate = currentDate;
        entries = new ArrayList<>();
    }

    //getters
    public String getEmail(){
        return email;
    }

    public Date getCurrentDate(){
        return currentDate;
    }

    public List<Entry> getEntries(){
        return entries;
    }

    //REQUIRES: email != null
    //MODIFIES: this
    //EFFECTS: sets email to given email
    public void setEmail(String email){
        this.email = email;
    }

    //REQUIRES: e != null, date of e is in the future
    //MODIFIES: this
    //EFFECTS: adds entry to calendar
    public void addEntry(Entry e){
        entries.add(e);
    }

    //MODIFIES: this
    //EFFECTS: removes entry from calendar
    public void removeEntry(Entry e){
        entries.remove(e);
    }

    //EFFECTS: prints out a list of all upcoming entries in the calendar
    public void printEntries(){
        System.out.println("Today is " + currentDate.getDatePretty());
        System.out.println("Upcoming: ");
        for(Entry e: entries){
            System.out.println(e.getLabel() + " (" + e.getClass().getSimpleName() +"): "
                    + e.getDate().getDateShort());
        }
    }

}
