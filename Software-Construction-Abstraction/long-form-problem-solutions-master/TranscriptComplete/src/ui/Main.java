package ui;

import model.Transcript;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        Transcript t1 = new Transcript("Jane Doe", 7830);
        Transcript t2 = new Transcript("Ada Lovelace", 8853);
        Transcript t3 = new Transcript("Charlie Babbage", 8561);

        t1.addGrade("CPSC-210", 3.5);
        t1.addGrade("ENGL-201", 4.0);
        t1.addGrade("CPSC-110", 3.1);

        t2.addGrade("CPSC-210", 2.7);
        t2.addGrade("CPSC-110", 2.0);

        t3.addGrade("CPSC-110", 3.0);
        t3.addGrade("PSYC-200", 2.8);
        t3.addGrade("CPSC-210", 2.1);


        //using Transcript objects
        System.out.print(t1.getStudentName() + ": ");
        t1.printTranscript();
        System.out.println("GPA: " + t1.getGPA());

        addSpace();
        System.out.println(t2.getStudentName() + ": ");
        System.out.println(t2.getCourseAndGrade("CPSC-210"));

        addSpace();
        System.out.println(t3.getStudentName() +", "+ t3.getStudentID());
        System.out.println("GPA: " + t3.getGPA());
        System.out.println("Grades achieved:");
        for(Double g : t3.getGrades()){
            System.out.println(g);
        }



        // extra problems used below
        addSpace();
        List<String> selectedCourses = new ArrayList<>();
        selectedCourses.add("CPSC-210");
        selectedCourses.add("CPSC-110");

        System.out.println(t1.getStudentName() + " CPSC average: "
                + t1.getAverageOverSelectedCourses(selectedCourses));
        System.out.println(t2.getStudentName() + " CPSC average: "
                + t2.getAverageOverSelectedCourses(selectedCourses));
        System.out.println(t3.getStudentName() + " CPSC average: "
                + t3.getAverageOverSelectedCourses(selectedCourses));

    }

    //EFFECTS: prints blank lines to console
    private static void addSpace(){
        System.out.println("\n");
    }

}
