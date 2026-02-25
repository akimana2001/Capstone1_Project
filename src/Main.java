import Models.*;
import Service.*;
import Exceptions.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UniversityManager manager = new UniversityManager();
        Scanner scanner = new Scanner(System.in);

        // Load saved data
        FileManager.loadStudents(manager.getStudents());
        FileManager.loadCourses(manager.getCourses());

        boolean running = true;

        while (running) {

            System.out.println("\n UNIVERSITY SYSTEM ");
            System.out.println("1. Register Student");
            System.out.println("2. Register Course");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. View Student Record");
            System.out.println("5. Generate Dean's List");
            System.out.println("6. Show Statistics (Average GPA + Top Student)");
            System.out.println("7. Save and Exit");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {

                // Register Student

                case 1:
                    try {
                        System.out.print("Student ID: ");
                        String id = scanner.nextLine();

                        System.out.print("Name: ");
                        String name = scanner.nextLine();

                        System.out.print("GPA: ");
                        double gpa = Double.parseDouble(scanner.nextLine());

                        System.out.print("Department: ");
                        String dept = scanner.nextLine();

                        Student student = new GraduateStudent(
                                "AUTO", name, "auto@email.com",
                                id, gpa, dept
                        );

                        manager.registerStudent(student);
                        System.out.println("Student registered successfully.");

                    } catch (IllegalArgumentException e) {
                        System.out.println("Registration failed: " + e.getMessage());
                    }
                    break;

                // Register Course

                case 2:
                    try {
                        System.out.print("Course Code: ");
                        String courseCode = scanner.nextLine();

                        System.out.print("Course Name: ");
                        String courseName = scanner.nextLine();

                        System.out.print("Credits: ");
                        int credits = Integer.parseInt(scanner.nextLine());

                        System.out.print("Capacity: ");
                        int capacity = Integer.parseInt(scanner.nextLine());

                        Course course = new Course(courseCode, courseName, credits, capacity);
                        manager.createCourse(course);

                        System.out.println("Course registered successfully.");

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid numeric input.");
                    }
                    break;

                // Enroll Student

                case 3:
                    System.out.print("Student ID: ");
                    String studentId = scanner.nextLine();

                    System.out.print("Course Code: ");
                    String enrollCourseCode = scanner.nextLine();

                    Student student = manager.getStudents().stream()
                            .filter(st -> st.getStudentID().equals(studentId))
                            .findFirst()
                            .orElse(null);

                    Course course = manager.getCourses().stream()
                            .filter(co -> co.getCourseCode().equals(enrollCourseCode))
                            .findFirst()
                            .orElse(null);

                    if (student == null || course == null) {
                        System.out.println("Student or Course not found.");
                        break;
                    }

                    try {
                        manager.enrollStudentInCourse(student, course);
                        System.out.println("Enrollment successful.");
                    } catch (CourseFullException | StudentAlreadyEnrolledException e) {
                        System.out.println("Enrollment failed: " + e.getMessage());
                    }
                    break;

                // View Student Record

                case 4:
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

                // Dean's List

                case 5:
                    System.out.println("Dean's List (GPA > 3.5)");
                    manager.getStudents().stream()
                            .filter(st -> st.getGPA() > 3.5)
                            .forEach(st ->
                                    System.out.println(st.getName() + " - " + st.getGPA())
                            );
                    break;

                // Lab2 Statistics

                case 6:
                    System.out.print("Department: ");
                    String department = scanner.nextLine();

                    double avg = manager.calculateAverageGPAByDepartment(department);
                    System.out.println("Average GPA: " + avg);

                    manager.findTopStudent()
                            .ifPresent(top ->
                                    System.out.println("Top Student: " + top.getName())
                            );
                    break;

                // Save & Exit
                case 7:
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