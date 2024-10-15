package model;

import java.util.ArrayList;
import java.util.List;

/**
 * INVARIANT: course list and grade list are the same size
 * each course has a grade associated, and vice versa, at matching indices
 */
public class Transcript {

    private String name;
    private int id;
    private List<String> courses;
    private List<Double> grades;

    public Transcript(String name, int id){
        this.name = name;
        this.id = id;
        courses = new ArrayList<>();
        grades = new ArrayList<>();
    }

    //getters
    public String getStudentName(){
        return name;
    }

    public int getStudentID(){
        return id;
    }

    public List<String> getCourseNames(){
        return courses;
    }

    public List<Double> getGrades(){
        return grades;
    }

    //REQUIRES: name != null
    //MODIFIES: this
    //EFFECTS: sets student name to given parameter
    public void setStudentName(String name){this.name = name;}

    //MODIFIES: this
    //EFFECTS: sets student id to given parameter
    public void setStudentID(int id){this.id = id;}

    //REQUIRES: course != null, 0.0 <= grade <= 4.0
    //MODIFIES: this
    //EFFECTS: adds course and grade to student record
    public void addGrade(String course, double grade){
        courses.add(course);
        grades.add(grade);
    }

    //REQUIRES: a course the student has already taken
    //EFFECTS: returns a String in the format CourseName: Grade
    public String getCourseAndGrade(String course){
        String str = "";
        int index = courses.indexOf(course.toUpperCase());

        if(index != -1){
            double grade = grades.get(index);
            str = course.toUpperCase() + ": " + grade;
        }

        return str;
    }

    //EFFECTS: returns grade averaged across all courses
    public double getGPA(){
        return calculateAverage(grades);
    }

    //EFFECTS: prints course names with grades earned
    //         in format CourseName: Grade, CourseName:Grade, etc.
    public void printTranscript(){
        for(String c : courses) {
            System.out.print(getCourseAndGrade(c) + ", ");
        }
        System.out.println();
    }



    // extra problems:

    //EFFECTS: calculates average over selectedGrades
    public double calculateAverage(List<Double> selectedGrades){
        int entries = 0;
        double sum = 0.0;

        for(double d : selectedGrades){
            sum += d;
            entries++;
        }

        if (entries!=0){
            return sum / entries;
        } else {
            return 0;
        }
    }

    //REQUIRES: a course the student has already taken
    //EFFECTS: returns correct grade for the given course
    public double getGradeByCourse(String course){
        int index = courses.indexOf(course.toUpperCase());

        if(index != -1){
            return grades.get(index);
        }

        return index;
    }

    //REQUIRES: a list of courses the student has already taken
    //EFFECTS: returns the average over selectedCourses
    public double getAverageOverSelectedCourses(List<String> selectedCourses){
        List<Double> gradesList = new ArrayList<>();

        for(String c : selectedCourses){
            gradesList.add(getGradeByCourse(c));
        }

        return calculateAverage(gradesList);
    }

}
