package Models;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {

    private String department;
    private List<Course> coursesTeaching = new ArrayList<>();

    public Instructor(String id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public List<Course> getCoursesTeaching() {
        return coursesTeaching;
    }

    public void addCourse(Course course) {
        coursesTeaching.add(course);
    }
}