package Models;

import java.util.HashMap;
import java.util.Map;

public abstract class Student extends Person {

    private String studentID;
    private double GPA;
    private String department;

    private Map<Course, Double> enrolledCourses = new HashMap<>();

    public Student(String id, String name, String email,
                   String studentID, double GPA, String department) {
        super(id, name, email);
        this.studentID = studentID;
        this.GPA = GPA;
        this.department = department;
    }


    public String getStudentID() {
        return studentID;
    }

    public double getGPA() {
        return GPA;
    }

    public String getDepartment() {
        return department;
    }

    public Map<Course, Double> getEnrolledCourses() {
        return enrolledCourses;
    }


    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public void enrollCourse(Course course, double grade) {
        enrolledCourses.put(course, grade);
    }


    public abstract double calculateTuition();
}