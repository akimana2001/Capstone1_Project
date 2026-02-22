package Models;

public class GraduateStudent extends Student {

    private static final double RATE_PER_CREDIT = 800.0;
    private static final double RESEARCH_FEE = 1500.0;

    public GraduateStudent(String id, String name, String email,
                           String studentID, double GPA, String department) {
        super(id, name, email, studentID, GPA, department);
    }

    @Override
    public double calculateTuition() {
        int totalCredits = getEnrolledCourses()
                .keySet()
                .stream()
                .mapToInt(Course::getCredits)
                .sum();

        return (totalCredits * RATE_PER_CREDIT) + RESEARCH_FEE;
    }
}