package Models;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private String courseCode;
    private String courseName;
    private int credits;
    private int capacity;

    private List<Student> enrolledStudents = new ArrayList<>();

    public Course(String courseCode, String courseName,
                  int credits, int capacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.capacity = capacity;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    public void addStudent(Student student) {
        if (!isFull()) {
            enrolledStudents.add(student);
        }
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseName +
                " (" + credits + " credits)";
    }
}