package Service;

import Models.*;
import Exceptions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UniversityManager {

    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    // Registering Student
    public void registerStudent(Student student) {

        boolean exists = students.stream()
                .anyMatch(s -> s.getStudentID()
                        .equals(student.getStudentID()));

        if (exists) {
            System.out.println("Student with ID "
                    + student.getStudentID() + " already exists.");
            return;
        }

        students.add(student);
    }

    // Creating Course
    public void createCourse(Course course) {

        boolean exists = courses.stream()
                .anyMatch(c -> c.getCourseCode()
                        .equalsIgnoreCase(course.getCourseCode()));

        if (exists) {
            System.out.println("Course "
                    + course.getCourseCode() + " already exists.");
            return;
        }

        courses.add(course);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    // Enrollment of the course
    public void enrollStudentInCourse(Student student, Course course)
            throws CourseFullException, StudentAlreadyEnrolledException {

        if (student == null || course == null) {
            throw new IllegalArgumentException("Student or Course cannot be null");
        }

        if (course.isFull()) {
            throw new CourseFullException(
                    "Course " + course.getCourseCode() + " is full.");
        }

        if (student.getEnrolledCourses().containsKey(course)) {
            throw new StudentAlreadyEnrolledException(
                    "Student already enrolled in "
                            + course.getCourseCode());
        }

        course.addStudent(student);
        student.enrollCourse(course, 0.0);
    }

    // Assigning Grades
    public void assignGrade(Student student, Course course, double grade) {

        if (!student.getEnrolledCourses().containsKey(course)) {
            System.out.println("Student not enrolled in this course.");
            return;
        }

        if (grade < 0 || grade > 100) {
            System.out.println("Grade must be between 0 and 100.");
            return;
        }

        student.getEnrolledCourses().put(course, grade);
    }

    //  GPA Average by Department
    public double calculateAverageGPAByDepartment(String department) {

        return students.stream()
                .filter(s -> s.getDepartment()
                        .equalsIgnoreCase(department))
                .mapToDouble(Student::getGPA)
                .average()
                .orElse(0.0);
    }

    // Finding the Top Performer Student
    public Optional<Student> findTopStudent() {

        return students.stream()
                .max(Comparator.comparingDouble(Student::getGPA));
    }
}