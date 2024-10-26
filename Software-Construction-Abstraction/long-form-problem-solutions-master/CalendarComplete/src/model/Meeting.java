package model;

import java.util.ArrayList;
import java.util.List;

public class Meeting extends Event {

    private List<String> attendees;

    //REQUIRES: date, time, label != null
    public Meeting(Date date, Time time, String label){
        super(date, time, label);
        attendees = new ArrayList<>();
    }

    //EFFECTS: returns list of email addresses of attendees
    public List<String> getAttendees(){
        return attendees;
    }

    //REQUIRES: a != null
    //MODIFIES: this
    //EFFECTS: adds attendee to list of invited people
    public void addAttendee(String a){
        attendees.add(a);
    }

    //REQUIRES: a != null
    //MODIFIES: this
    //EFFECTS: removes attendee from list of invitees
    public void removeAttendee(String a){
        attendees.remove(a);
    }

    //EFFECTS: prints out the email of each attendee with the prefix "Inviting: "
    public void sendInvites(){
        for(String a : attendees){
            System.out.println("Inviting: " + a);
        }
    }
}
