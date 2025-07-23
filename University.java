import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class University {
    // Static counters to keep track of the total number of students, teachers, and courses
    private static int totalStudents = 0;
    private static int totalTeachers = 0;
    private static int totalCourses = 0;

    // Lists to store students, teachers, and courses
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Teacher> teachers = new ArrayList<>();
    private static ArrayList<Course> courses = new ArrayList<>();

    // Method to add a student
    public static void addStudent(Student student) {
        if (student == null) {
            System.out.println("Error: Cannot add a null student.");
            return;
        }
        if (students.contains(student)) {
            System.out.println("Error: Student " + student.getName() + " is already added.");
            return;
        }
        students.add(student);
        totalStudents++;
        System.out.println("Student " + student.getName() + " added successfully.");
    }

    // Method to add a teacher
    public static void addTeacher(Teacher teacher) {
        if (teacher == null) {
            System.out.println("Error: Cannot add a null teacher.");
            return;
        }
        if (teachers.contains(teacher)) {
            System.out.println("Error: Teacher " + teacher.getName() + " is already added.");
            return;
        }
        teachers.add(teacher);
        totalTeachers++;
        System.out.println("Teacher " + teacher.getName() + " added successfully.");
    }

    // Method to add a course
    public static void addCourse(Course course) {
        if (course == null) {
            System.out.println("Error: Cannot add a null course.");
            return;
        }
        if (courses.contains(course)) {
            System.out.println("Error: Course " + course.getCourseTitle() + " is already added.");
            return;
        }
        courses.add(course);
        totalCourses++;
        System.out.println("Course " + course.getCourseTitle() + " added successfully.");
    }

    // Static method to display the system stats
    public static void displaySystemStats() {
        System.out.println("University System Stats:");
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Total Teachers: " + totalTeachers);
        System.out.println("Total Courses: " + totalCourses);
    }

    // Method to search for students by name
    public static List<Student> searchStudentByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Name cannot be null or empty.");
            return new ArrayList<>(); // Return an empty list to avoid null pointers
        }

        ArrayList<Student> matchingStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                matchingStudents.add(student);
            }
        }
        if (matchingStudents.isEmpty()) {
            System.out.println("No students found with the name: " + name);
        } else {
            System.out.println("Found students: ");
            for (Student student : matchingStudents) {
                System.out.println(student); // Uses the toString() method in the Student class
            }
        }
        return matchingStudents;
    }

    // Method to filter courses by credit hours
    public static List<Course> filterCoursesByCredits(int minCredits) {
        if (minCredits <= 0) {
            System.out.println("Error: Minimum credits must be greater than zero.");
            return new ArrayList<>(); // Return an empty list for invalid input
        }

        ArrayList<Course> matchingCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCreditHours() >= minCredits) {
                matchingCourses.add(course);
            }
        }
        if (matchingCourses.isEmpty()) {
            System.out.println("No courses found with at least " + minCredits + " credits.");
        } else {
            System.out.println("Courses with at least " + minCredits + " credits: ");
            for (Course course : matchingCourses) {
                System.out.println(course); // Uses the toString() method in the Course class
            }
        }
        return matchingCourses;
    }

    // Method to save data to a file
    public static void saveData(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Save students
            writer.write("Students:\n");
            for (Student student : students) {
                writer.write(student.getName() + "," + student.getAge() + "," + student.getStudentID() + ","
                        + student.getAddress() + "," + student.getCgpa() + "\n");
            }

            // Save teachers
            writer.write("Teachers:\n");
            for (Teacher teacher : teachers) {
                writer.write(teacher.getTeacherID() + "," + teacher.getName() + "," + teacher.getAge() + ","
                        + teacher.getSpecialization() + "\n");
            }

            // Save courses
            writer.write("Courses:\n");
            for (Course course : courses) {
                writer.write(course.getCourseID() + "," + course.getCourseTitle() + "," + course.getCreditHours() + ","
                        + course.getTeacher().getName() + "\n");
            }

            System.out.println("Data saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error: Could not save data to file.");
            e.printStackTrace();
        }
    }

    // Method to load data from a file
    public static void loadData(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean readingStudents = false;
            boolean readingTeachers = false;
            boolean readingCourses = false;

            while ((line = reader.readLine()) != null) {
                if (line.equals("Students:")) {
                    readingStudents = true;
                    readingTeachers = false;
                    readingCourses = false;
                } else if (line.equals("Teachers:")) {
                    readingStudents = false;
                    readingTeachers = true;
                    readingCourses = false;
                } else if (line.equals("Courses:")) {
                    readingStudents = false;
                    readingTeachers = false;
                    readingCourses = true;
                } else {
                    if (readingStudents) {
                        String[] studentData = line.split(",");
                        if (studentData.length == 5) {
                            Student student = new Student(studentData[0], Integer.parseInt(studentData[1]), studentData[2],
                                    studentData[3], new ArrayList<>(), Double.parseDouble(studentData[4]), new Date());
                            students.add(student);
                            totalStudents++;
                        }
                    } else if (readingTeachers) {
                        String[] teacherData = line.split(",");
                        if (teacherData.length == 4) {
                            Teacher teacher = new Teacher(Integer.parseInt(teacherData[0]), teacherData[1],
                                    Integer.parseInt(teacherData[2]), teacherData[3], 0, new Date(), "unknown@example.com");
                            teachers.add(teacher);
                            totalTeachers++;
                        }
                    } else if (readingCourses) {
                        String[] courseData = line.split(",");
                        if (courseData.length == 4) {
                            Teacher courseTeacher = teachers.stream()
                                    .filter(t -> t.getName().equals(courseData[3]))
                                    .findFirst().orElse(null);
                            if (courseTeacher != null) {
                                Course course = new Course(Integer.parseInt(courseData[0]), courseData[1],
                                        Integer.parseInt(courseData[2]), courseTeacher);
                                courses.add(course);
                                totalCourses++;
                            }
                        }
                    }
                }
            }

            System.out.println("Data loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error: Could not load data from file.");
            e.printStackTrace();
        }
    }
}
