import Models.*;
import Service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UniversityManager manager = new UniversityManager();
        Scanner scanner = new Scanner(System.in);

        // Loading data
        FileManager.loadStudents(manager.getStudents());
        FileManager.loadCourses(manager.getCourses());

        boolean running = true;

        while (running) {

            System.out.println("\n UNIVERSITY SYSTEM");
            System.out.println("1. Register Student");
            System.out.println("2. Enroll in Course");
            System.out.println("3. View Student Record");
            System.out.println("4. Generate Dean's List");
            System.out.println("5. Save and Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Student ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("GPA: ");
                    double gpa = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Department: ");
                    String dept = scanner.nextLine();


                    Student student = new GraduateStudent(
                            "AUTO", name, "auto@email.com",
                            id, gpa, dept
                    );

                    manager.registerStudent(student);
                    System.out.println("Student registered.");
                    break;

                case 2:
                    System.out.print("Student ID: ");
                    String studentId = scanner.nextLine();

                    System.out.print("Course Code: ");
                    String courseCode = scanner.nextLine();

                    Student s = manager.getStudents().stream()
                            .filter(st -> st.getStudentID().equals(studentId))
                            .findFirst()
                            .orElse(null);

                    Course c = manager.getCourses().stream()
                            .filter(co -> co.getCourseCode().equals(courseCode))
                            .findFirst()
                            .orElse(null);

                    if (s == null || c == null) {
                        System.out.println("Student or Course not found.");
                        break;
                    }

                    try {
                        manager.enrollStudentInCourse(s, c);
                        System.out.println("Enrollment successful.");
                    } catch (Exception e) {
                        System.out.println("Enrollment failed: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Student ID: ");
                    String sid = scanner.nextLine();

                    manager.getStudents().stream()
                            .filter(st -> st.getStudentID().equals(sid))
                            .findFirst()
                            .ifPresentOrElse(st -> {
                                System.out.println("Name: " + st.getName());
                                System.out.println("GPA: " + st.getGPA());
                                System.out.println("Courses: " + st.getEnrolledCourses().keySet());
                            }, () -> System.out.println("Student not found."));
                    break;

                case 4:
                    System.out.println("Dean's List (GPA > 3.5)");
                    manager.getStudents().stream()
                            .filter(st -> st.getGPA() > 3.5)
                            .forEach(st ->
                                    System.out.println(st.getName() + " - " + st.getGPA())
                            );
                    break;

                case 5:
                    FileManager.saveStudents(manager.getStudents());
                    FileManager.saveCourses(manager.getCourses());
                    System.out.println("Data saved. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}