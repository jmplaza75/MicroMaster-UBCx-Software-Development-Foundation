package tests;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EntryTest {

    private Reminder reminder;
    private Meeting meeting;
    private Event event;
    private Date d1;
    private Date d2;
    private Time t1;
    private Time t2;

    @Before
    public void setUp(){
        d1 = new Date (3, 6, 2017);
        d2 = new Date (1, 1, 2018);
        t1 = new Time(13, 2);
        t2 = new Time(10, 13);
        event = new Event(d1, t1, "Jen's birthday");
        meeting = new Meeting(d2, t2, "Performance review");
        reminder = new Reminder(d2, t1, "Make New Year's resolutions");
    }

    @Test
    public void testConstructor(){
        assertEquals(d1, event.getDate());
        assertEquals(d2, meeting.getDate());
        assertEquals(t1, event.getTime());
        assertEquals(t2, meeting.getTime());
        assertEquals("Jen's birthday", event.getLabel());
        assertEquals("Make New Year's resolutions", reminder.getLabel());
    }

    @Test
    public void testEntry(){
        meeting.setRepeating(182); //6 months
        assertTrue(meeting.isRepeating());
        assertEquals(182, meeting.getIntervalOfRepetition());
        meeting.setRepeating(0);
        assertFalse(meeting.isRepeating());
    }

    @Test
    public void testEvent(){
        event.setReminder(reminder);
        assertEquals(reminder, event.getReminder());
        reminder = new Reminder(d1, t2, "Work launch party");
        reminder.setNote("Bring chips and dip");
        event.setReminder(reminder);
        assertEquals("Bring chips and dip", event.getReminder().getNote());
    }

    @Test
    public void testMeeting(){
        List<String> attendees = new ArrayList<>();
        attendees.add("Devon");
        attendees.add("Aine");
        attendees.add("Lironne");
        meeting.addAttendee("Devon");
        meeting.addAttendee("Aine");
        meeting.addAttendee("Lironne");
        assertEquals(attendees, meeting.getAttendees());
        attendees.remove("Devon");
        meeting.removeAttendee("Devon");
        assertEquals(attendees, meeting.getAttendees());
    }

    @Test
    public void testReminder(){
        assertEquals("No note added", reminder.getNote());
        reminder.setNote("Eat more vegetables");
        assertEquals("Eat more vegetables", reminder.getNote());
    }

}
