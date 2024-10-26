package tests;

import model.Transcript;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TranscriptTest {

    private Transcript transcript;
    private static final float TOL = 1.000f;

    @Before
    public void setUp(){
        transcript = new Transcript("Jane Doe", 7001);
    }

    @Test
    public void testConstructor(){
        assertEquals(transcript.getStudentName(), "Jane Doe");
        assertEquals(transcript.getStudentID(), 7001);
        assertEquals(transcript.getCourseNames().size(),0);
        assertEquals(transcript.getGrades().size(),0);
    }

    @Test
    public void testSetters(){
        transcript.setStudentID(4000);
        transcript.setStudentName("John Smith");
        assertEquals(transcript.getStudentName(), "John Smith");
        assertEquals(4000, transcript.getStudentID());
    }

    @Test
    public void testAddGrade(){
        transcript.addGrade("BUSI 300", 3.9);
        assertEquals(transcript.getCourseNames().size(),
                transcript.getGrades().size(), 1);

        loadGrades();
        assertEquals(transcript.getCourseNames().size(), transcript.getGrades().size());
    }


    @Test
    public void testGetCourseAndGrade(){
        loadGrades();
        assertEquals(transcript.getCourseAndGrade("PHYS 203"), "PHYS 203: 2.5");
        assertEquals(transcript.getCourseAndGrade("ENGL 100"), "ENGL 100: 3.2");
    }

    @Test
    public void testGetGPA(){
        loadGrades();
        double avg = ((2.5 + 3.2) / 2);
        assertEquals(avg, transcript.getGPA(), TOL);

        transcript.addGrade("JPNS 105", 3.8);
        avg = ((2.5 + 3.2 + 3.8) / 3);
        assertEquals(avg, transcript.getGPA(), TOL);
    }

    // 'tests' printTranscript() in main

    private void loadGrades(){
        transcript.addGrade("ENGL 100", 3.2);
        transcript.addGrade("PHYS 203", 2.5);
    }


    //tests for extra problems:

    @Test
    public void testGetGradeByCourse(){
        loadGrades();
        assertEquals(transcript.getGradeByCourse("ENGL 100"), 3.2, TOL);
        assertEquals(transcript.getGradeByCourse("PHYS 203"), 2.5, TOL);
    }

    @Test
    public void testGetAverageOverSelectedCourses(){
        loadGrades();
        transcript.addGrade("SOCI 200", 1.5);

        List<String> selectedCourses = new ArrayList<>();
        selectedCourses.add("SOCI 200");
        selectedCourses.add("ENGL 100");

        double avg = (1.5 + 3.2) / 2;

        assertEquals(transcript.getAverageOverSelectedCourses(selectedCourses), avg, TOL);
    }


}