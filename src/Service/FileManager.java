package Service;

import Models.*;

import java.io.*;
import java.util.List;

public class FileManager {

    private static final String STUDENT_FILE = "students.txt";
    private static final String COURSE_FILE = "courses.txt";

    // Saving Records

    public static void saveStudents(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_FILE))) {
            for (Student s : students) {
                writer.println(
                        s.getStudentID() + "," +
                                s.getName() + "," +
                                s.getGPA() + "," +
                                s.getDepartment()
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving students.");
        }
    }

    public static void saveCourses(List<Course> courses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COURSE_FILE))) {
            for (Course c : courses) {
                writer.println(
                        c.getCourseCode() + "," +
                                c.getCourseName() + "," +
                                c.getCredits()
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving courses.");
        }
    }

    // Reading the Records

    public static void loadStudents(List<Student> students) {
        File file = new File(STUDENT_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                Student student = new GraduateStudent(
                        "AUTO",
                        data[1],
                        "auto@email.com",
                        data[0],
                        Double.parseDouble(data[2]),
                        data[3]
                );

                students.add(student);
            }
        } catch (IOException e) {
            System.out.println("Error loading students.");
        }
    }

    public static void loadCourses(List<Course> courses) {
        File file = new File(COURSE_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                Course course = new Course(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2]),
                        50
                );

                courses.add(course);
            }
        } catch (IOException e) {
            System.out.println("Error loading courses.");
        }
    }
}