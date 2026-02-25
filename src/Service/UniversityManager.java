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


    // The  Registration of the Studtens and the Courses


    public void registerStudent(Student student) {
        students.add(student);
    }

    public void createCourse(Course course) {
        courses.add(course);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }


    // The Enrollments of the students to the course

    public void enrollStudentInCourse(Student student, Course course)
            throws CourseFullException, StudentAlreadyEnrolledException {

        //   Checking the capacity of the Course
        if (course.isFull()) {
            throw new CourseFullException(
                    "Course " + course.getCourseCode() + " is full.");
        }

        // Duplicating the  enrollment check
        if (student.getEnrolledCourses().containsKey(course)) {
            throw new StudentAlreadyEnrolledException(
                    "Student already enrolled in " + course.getCourseCode());
        }

        // Performing enrollment
        course.addStudent(student);
        student.enrollCourse(course, 0.0); // default grade
    }


    // Assigning the  Grades


    public void assignGrade(Student student, Course course, double grade) {

        if (student.getEnrolledCourses().containsKey(course)) {
            student.getEnrolledCourses().put(course, grade);
        }
    }


    // Calculating the Average GPA by department
    public double calculateAverageGPAByDepartment(String department) {

        return students.stream()
                .filter(s -> s.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Student::getGPA)
                .average()
                .orElse(0.0);
    }

    // Finding the top performing student
    public Optional<Student> findTopStudent() {

        return students.stream()
                .max(Comparator.comparingDouble(Student::getGPA));
    }
}