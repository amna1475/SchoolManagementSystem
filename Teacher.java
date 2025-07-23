import java.io.FileWriter;
import java.io.IOException;

public class Teacher extends Person implements Reportable {
    // Attributes
    private int teacherID;
    private String specialization;
    private Course[] courses;
    private int courseCount = 0;

    // Constructor with error handling for invalid ID and maxCourses
    public Teacher(int teacherID, String name, int age, String specialization, int maxCourses, Date dateOfBirth, String email) {
        super(name, age, dateOfBirth, email); // Pass dateOfBirth and email to the parent constructor (Person)
        if (teacherID < 0) {
            System.out.println("Error: Teacher ID cannot be negative.");
            return;
        }
        if (maxCourses < 0) {
            System.out.println("Error: Maximum courses cannot be negative.");
            return;
        }
        this.teacherID = teacherID;
        this.specialization = specialization;
        this.courses = new Course[maxCourses];
    }

    // Getter methods
    public int getTeacherID() {
        return teacherID;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setTeacherID(int teacherID){ 
        if(teacherID > 0) {
            this.teacherID = teacherID;
        }
    }

    public void setSpecialization(String speaclization){ 
        this.specialization = speaclization;
    }

    // Method to assign a course to the teacher with error handling
    public void assignCourse(Course course) {
        if (course == null) {
            System.out.println("Error: Cannot assign a null course.");
            return;
        }
        if (courseCount >= courses.length) {
            System.out.println("Error: Cannot assign more courses. Max capacity reached.");
            return;
        }
        courses[courseCount++] = course;
        System.out.println(getName() + " assigned to " + course.getCourseTitle());
    }

    // Method to generate the teacher workload report with error handling for empty courses
    public String generateWorkloadReport() {
        String report = "Teacher: " + getName() + " (ID: " + teacherID + ")\n";
        report += "Courses taught:\n";

        if (courseCount == 0) {
            report += "No courses assigned.\n";
        } else {
            for (int i = 0; i < courseCount; i++) {
                report += courses[i].getCourseTitle() + "\n";
            }
        }

        return report;
    }

    // Overridden toString() method to display teacher details
    @Override
    public String toString() {
        String details = "Teacher ID: " + teacherID + "\n";
        details += "Teacher Name: " + getName() + "\n";
        details += "Age: " + getAge() + "\n";
        details += "Date of Birth: " + getDateOfBirth().getDay() + "-" + getDateOfBirth().getMonth() + "-" + getDateOfBirth().getYear() + "\n";
        details += "Email: " + getEmail() + "\n";  // Displaying email from the Person class
        details += "Specialization: " + specialization + "\n";
        details += "Courses taught: \n";
        for (int i = 0; i < courseCount; i++) {
            details += "Course ID: " + courses[i].getCourseID() + ", Name: " + courses[i].getCourseTitle() + "\n";
        }
        return details; // Return the complete teacher details as a string
    }

    // Implement generateReport method from Reportable interface with error handling
    @Override
    public void generateReport() {
        if (courseCount == 0) {
            System.out.println("Error: No courses assigned to teacher " + getName() + " for the report.");
            return;
        }
        System.out.println("Generating report for Teacher: " + getName());
        System.out.println("Teacher ID: " + teacherID);
        System.out.println("Specialization: " + specialization);
        System.out.println("Courses taught: ");
        for (int i = 0; i < courseCount; i++) {
            System.out.println(courses[i].getCourseTitle());
        }
    }

    // Implement exportToFile method from Reportable interface with exception handling
    @Override
    public void exportToFile() {
        try (FileWriter writer = new FileWriter("teacher_report.txt")) {
            if (courseCount == 0) {
                System.out.println("Error: No courses assigned to export.");
                return;
            }
            writer.write("Teacher ID: " + teacherID + "\n");
            writer.write("Teacher Name: " + getName() + "\n");
            writer.write("Age: " + getAge() + "\n");
            writer.write("Specialization: " + specialization + "\n");
            writer.write("Date of Birth: " + getDateOfBirth().getDay() + "-" + getDateOfBirth().getMonth() + "-" + getDateOfBirth().getYear() + "\n");
            writer.write("Email: " + getEmail() + "\n");
            writer.write("Courses taught: \n");
            for (int i = 0; i < courseCount; i++) {
                writer.write(courses[i].getCourseTitle() + "\n");
            }
            System.out.println("Report exported to teacher_report.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while exporting the report.");
            e.printStackTrace();
        }
    }
}
