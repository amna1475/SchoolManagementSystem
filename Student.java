import java.util.ArrayList;

public class Student extends Person {
    // Attributes
    private String studentID;
    private String address;
    private ArrayList<Course> courses;
    private double cgpa;

    // Default constructor
    public Student() {
        super(); // Calls the default constructor of the Person class
        this.studentID = "null";
        this.address = "null";
        this.courses = new ArrayList<>();
        this.cgpa = 0.0;
    }

    // Parameterized constructor with error handling
    public Student(String name, int age, String studentID, String address, ArrayList<Course> courses, double cgpa, Date dateOfBirth) {
        super(name, age, dateOfBirth, "unknown@example.com"); // Pass dateOfBirth to the parent constructor (Person)
        try {
            setStudentID(studentID);
            setAddress(address);
            setCourses(courses);
            setCgpa(cgpa);
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating Student: " + e.getMessage());
            this.studentID = "null";
            this.address = "null";
            this.courses = new ArrayList<>();
            this.cgpa = 0.0;
        }
    }

    // Getter methods
    public String getStudentID() {
        return studentID;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public double getCgpa() {
        return cgpa;
    }

    // Setter methods with error handling
    public void setStudentID(String studentID) {
        if (studentID == null || studentID.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        this.studentID = studentID;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        this.address = address;
    }

    public void setCourses(ArrayList<Course> courses) {
        if (courses == null) {
            throw new IllegalArgumentException("Courses list cannot be null.");
        }
        this.courses = courses;
    }

    public void setCgpa(double cgpa) {
        if (cgpa < 0.0 || cgpa > 4.0) {
            throw new IllegalArgumentException("CGPA must be between 0.0 and 4.0.");
        }
        this.cgpa = cgpa;
    }

    // Enroll in a course with validation for duplicates
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        if (!courses.contains(course)) {
            courses.add(course);
            System.out.println("Student " + studentID + " successfully enrolled in " + course.getCourseTitle());
        } else {
            System.out.println("Already enrolled in " + course.getCourseTitle());
        }
    }

    // Overridden toString() method to return student details
    @Override
    public String toString() {
        return "Student ID: " + studentID + "\n" +
               "Name: " + getName() + "\n" +
               "Age: " + getAge() + "\n" +
               "Date of Birth: " + getDateOfBirth().getDay() + "-" + getDateOfBirth().getMonth() + "-" + getDateOfBirth().getYear() + "\n" +
               "Address: " + address + "\n" +
               "Enrolled Courses: " + (courses.isEmpty() ? "No courses enrolled" : courses) + "\n" +
               "CGPA: " + cgpa;
    }
}
