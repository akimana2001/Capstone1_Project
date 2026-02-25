import Models.*;
import Service.*;
import Exceptions.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UniversityManager manager = new UniversityManager();
        Scanner scanner = new Scanner(System.in);

        FileManager.loadStudents(manager.getStudents());
        FileManager.loadCourses(manager.getCourses());
        FileManager.loadEnrollments(manager.getStudents(), manager.getCourses());

        boolean running = true;

        while (running) {

            System.out.println("\n UNIVERSITY SYSTEM ");
            System.out.println("1. Register Student");
            System.out.println("2. Register Course");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. View Student Record");
            System.out.println("5. Generate Dean's List");
            System.out.println("6. Show Statistics");
            System.out.println("7. Save and Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    System.out.print("1. Graduate  2. Undergraduate: ");
                    int type = Integer.parseInt(scanner.nextLine());

                    System.out.print("Student ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("GPA: ");
                    double gpa = Double.parseDouble(scanner.nextLine());

                    System.out.print("Department: ");
                    String dept = scanner.nextLine();

                    Student student = (type == 1)
                            ? new GraduateStudent("AUTO", name, email, id, gpa, dept)
                            : new UndergraduateStudent("AUTO", name, email, id, gpa, dept);

                    manager.registerStudent(student);
                    break;

                case 2:
                    System.out.print("Course Code: ");
                    String code = scanner.nextLine();

                    System.out.print("Course Name: ");
                    String cname = scanner.nextLine();

                    System.out.print("Credits: ");
                    int credits = Integer.parseInt(scanner.nextLine());

                    System.out.print("Capacity: ");
                    int cap = Integer.parseInt(scanner.nextLine());

                    manager.createCourse(new Course(code, cname, credits, cap));
                    break;

                case 3:
                    System.out.print("Student ID: ");
                    String sid = scanner.nextLine();

                    System.out.print("Course Code: ");
                    String cc = scanner.nextLine();

                    Student st = manager.getStudents().stream()
                            .filter(s -> s.getStudentID().equals(sid))
                            .findFirst().orElse(null);

                    Course co = manager.getCourses().stream()
                            .filter(c -> c.getCourseCode().equals(cc))
                            .findFirst().orElse(null);

                    try {
                        manager.enrollStudentInCourse(st, co);
                        System.out.println("Enrollment successful.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Student ID: ");
                    String sidView = scanner.nextLine();

                    manager.getStudents().stream()
                            .filter(s -> s.getStudentID().equals(sidView))
                            .findFirst()
                            .ifPresentOrElse(s -> {
                                System.out.println("Name: " + s.getName());
                                System.out.println("GPA: " + s.getGPA());
                                System.out.println("Courses:");
                                s.getEnrolledCourses().forEach((c, g) ->
                                        System.out.println(c.getCourseCode() + " -> " + g));
                            }, () -> System.out.println("Student not found"));
                    break;

                case 5:
                    System.out.println("Dean's List (GPA > 3.5)");
                    manager.getStudents().stream()
                            .filter(s -> s.getGPA() > 3.5)
                            .forEach(s ->
                                    System.out.println(s.getName() + " - " + s.getGPA()));
                    break;

                case 6:
                    System.out.print("Department: ");
                    String d = scanner.nextLine();

                    double avg = manager.calculateAverageGPAByDepartment(d);
                    System.out.println("Average GPA: " + avg);

                    manager.findTopStudent()
                            .ifPresent(s -> System.out.println("Top Student: " + s.getName()));
                    break;

                case 7:
                    FileManager.saveStudents(manager.getStudents());
                    FileManager.saveCourses(manager.getCourses());
                    FileManager.saveEnrollments(manager.getStudents());
                    System.out.println("Saved. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}