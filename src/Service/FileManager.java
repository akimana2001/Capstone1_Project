package Service;

import Models.*;

import java.io.*;
import java.util.*;

public class FileManager {

    private static final String STUDENT_FILE = "students.txt";
    private static final String COURSE_FILE = "courses.txt";
    private static final String ENROLL_FILE = "enrollments.txt";

    //  Save files

    public static void saveStudents(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_FILE))) {

            for (Student s : students) {
                String type = (s instanceof GraduateStudent) ? "GRAD" : "UNDER";

                writer.write(type + "," +
                        s.getStudentID() + "," +
                        s.getName() + "," +
                        s.getEmail() + "," +
                        s.getGPA() + "," +
                        s.getDepartment());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving students.");
        }
    }

    public static void saveCourses(List<Course> courses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSE_FILE))) {

            for (Course c : courses) {
                writer.write(c.getCourseCode() + "," +
                        c.getCourseName() + "," +
                        c.getCredits() + "," +
                        c.getEnrolledStudents().size());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving courses.");
        }
    }

    public static void saveEnrollments(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ENROLL_FILE))) {

            for (Student s : students) {
                for (Map.Entry<Course, Double> entry : s.getEnrolledCourses().entrySet()) {
                    writer.write(s.getStudentID() + "," +
                            entry.getKey().getCourseCode() + "," +
                            entry.getValue());
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error saving enrollments.");
        }
    }

    //  Loading files

    public static void loadStudents(List<Student> students) {
        File file = new File(STUDENT_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] d = line.split(",");

                Student s = d[0].equals("GRAD")
                        ? new GraduateStudent("AUTO", d[2], d[3], d[1], Double.parseDouble(d[4]), d[5])
                        : new UndergraduateStudent("AUTO", d[2], d[3], d[1], Double.parseDouble(d[4]), d[5]);

                students.add(s);
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
                String[] d = line.split(",");

                courses.add(new Course(
                        d[0], d[1],
                        Integer.parseInt(d[2]), 50));
            }

        } catch (IOException e) {
            System.out.println("Error loading courses.");
        }
    }

    public static void loadEnrollments(List<Student> students, List<Course> courses) {

        File file = new File(ENROLL_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] d = line.split(",");

                Student s = students.stream()
                        .filter(st -> st.getStudentID().equals(d[0]))
                        .findFirst().orElse(null);

                Course c = courses.stream()
                        .filter(co -> co.getCourseCode().equals(d[1]))
                        .findFirst().orElse(null);

                if (s != null && c != null) {
                    s.getEnrolledCourses().put(c, Double.parseDouble(d[2]));
                    c.getEnrolledStudents().add(s);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading enrollments.");
        }
    }
}