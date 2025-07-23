import java.util.ArrayList;
import java.util.Collections;

public class Course {
    // Attributes
    private int courseID;
    private String courseTitle;
    private int creditHours;
    private Teacher teacher;
    private ArrayList<Student> students;  // List of enrolled students
    private ArrayList<Double> grades;     // List of grades corresponding to students
    private int countCourses;             // Tracks the number of students in the course

    // Constructor
    public Course(int courseID, String courseTitle, int creditHours, Teacher teacher) {
        if (courseID <= 0) {
            throw new IllegalArgumentException("Course ID must be positive.");
        }
        if (courseTitle == null || courseTitle.isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be null or empty.");
        }
        if (creditHours <= 0) {
            throw new IllegalArgumentException("Credit hours must be greater than zero.");
        }
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null.");
        }

        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.teacher = teacher;
        this.students = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.countCourses = 0; // Initially no students
    }

    // Getter methods
    public int getCourseID() {
        return courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public int getCountCourses() {
        return countCourses;
    }

    //setters
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    

    // Method to add a student with their grade
    public void addStudentWithGrade(Student student, double grade) {
        try {
            if (student == null) {
                throw new IllegalArgumentException("Student cannot be null.");
            }

            if (students.contains(student)) {
                System.out.println("Student " + student.getName() + " is already enrolled. Updating grade.");
                int index = students.indexOf(student);
                grades.set(index, grade); // Update grade for the existing student
            } else {
                students.add(student);
                grades.add(grade); // Add grade for the new student
                countCourses++;
                System.out.println(student.getName() + " added to " + courseTitle + " with grade: " + grade);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    // Method to remove a student and their grade
    public void removeStudent(Student student) {
        try {
            if (student == null) {
                throw new IllegalArgumentException("Student cannot be null.");
            }

            if (students.contains(student)) {
                int index = students.indexOf(student);
                students.remove(index);  // Remove student
                grades.remove(index);    // Remove corresponding grade
                countCourses--;
                System.out.println(student.getName() + " removed from " + courseTitle);
            } else {
                System.out.println("Student not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error removing student: " + e.getMessage());
        }
    }

    // Method to calculate the average grade of all students
    public double calculateAverageGrade() {
        try {
            if (grades.isEmpty()) {
                throw new IllegalStateException("No grades available for this course.");
            }

            double totalGrades = 0.0;
            for (double grade : grades) {
                totalGrades += grade; // Sum up all grades
            }

            double average = totalGrades / grades.size();
            System.out.println("Average grade for " + courseTitle + ": " + average);
            return average;
        } catch (IllegalStateException e) {
            System.out.println("Error calculating average grade: " + e.getMessage());
            return 0.0;
        }
    }

    // Method to calculate the median grade
    public void calculateMedianGrade() {
        try {
            if (grades.isEmpty()) {
                throw new IllegalStateException("No students available to calculate the median.");
            }

            // Sort grades
            ArrayList<Double> sortedGrades = new ArrayList<>(grades);
            Collections.sort(sortedGrades);

            double median;
            int size = sortedGrades.size();
            if (size % 2 == 1) {
                // Odd number of grades
                median = sortedGrades.get(size / 2);
            } else {
                // Even number of grades
                median = (sortedGrades.get(size / 2 - 1) + sortedGrades.get(size / 2)) / 2;
            }

            System.out.println("Median grade for " + courseTitle + ": " + median);
        } catch (IllegalStateException e) {
            System.out.println("Error calculating median grade: " + e.getMessage());
        }
    }

    // Method to generate a report of students and grades in the course
    public String generateStudentReport() {
        String report = "Course: " + courseTitle + "\n";
        report += "Enrolled Students and Grades:\n";

        try {
            if (students.isEmpty()) {
                throw new IllegalStateException("No students enrolled in this course.");
            }

            for (int i = 0; i < students.size(); i++) {
                report += students.get(i).getName() + " (ID: " + students.get(i).getStudentID() + "), Grade: " + grades.get(i) + "\n";
            }
        } catch (IllegalStateException e) {
            report += "Error: " + e.getMessage();
        }

        return report;
    }

    // Overridden toString() method to return a string representation of the course
    @Override
    public String toString() {
        String details = "Course ID: " + courseID + "\n";
        details += "Course Title: " + courseTitle + "\n";
        details += "Credit Hours: " + creditHours + "\n";
        details += "Teacher: " + teacher.getName() + "\n";
        details += "Number of Students Enrolled: " + countCourses + "\n";
        details += "Students enrolled in this course: \n";

        try {
            if (students.isEmpty()) {
                throw new IllegalStateException("No students enrolled.");
            }

            for (int i = 0; i < students.size(); i++) {
                details += "Student ID: " + students.get(i).getStudentID() +
                           ", Name: " + students.get(i).getName() +
                           ", Grade: " + grades.get(i) + "\n";
            }
        } catch (IllegalStateException e) {
            details += "Error: " + e.getMessage();
        }

        return details;
    }
}
