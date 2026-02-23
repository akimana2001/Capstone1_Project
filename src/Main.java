import Models.*;
import Service.UniversityManager;
import Exceptions.*;

public class Main {

    public static void main(String[] args) {

        UniversityManager manager = new UniversityManager();


        Student s1 = new GraduateStudent(
                "1", "Alice", "alice@email.com",
                "S001", 3.8, "Computer Science");

        Student s2 = new GraduateStudent(
                "2", "Bob", "bob@email.com",
                "S002", 3.5, "Computer Science");

        // Assigning courses
        Course c1 = new Course("CS101", "Java Programming", 3, 1);
        Course c2 = new Course("CS102", "Python", 4, 2);

        // Registering students and course
        manager.registerStudent(s1);
        manager.registerStudent(s2);
        manager.createCourse(c1);

        // Trying enrollment
        try {
            manager.enrollStudentInCourse(s1, c1);
            manager.enrollStudentInCourse(s2, c1);
        } catch (CourseFullException | StudentAlreadyEnrolledException e) {
            System.out.println("Enrollment failed: " + e.getMessage());
        }

        // Assigning the grades
        manager.assignGrade(s1, c1, 4.0);

        // Calculating average GPA
        double avg = manager.calculateAverageGPAByDepartment("Computer Science");
        System.out.println("Average GPA (Computer Science): " + avg);

        // Finding top student
        manager.findTopStudent()
                .ifPresent(student ->
                        System.out.println("Top Student: " + student.getName())
                );
    }
}