package Models;

public class UndergraduateStudent extends Student {

    private static final double FLAT_RATE = 5000.0;

    public UndergraduateStudent(String id, String name, String email,
                                String studentID, double GPA, String department) {
        super(id, name, email, studentID, GPA, department);
    }

    @Override
    public double calculateTuition() {
        return FLAT_RATE;
    }
}
