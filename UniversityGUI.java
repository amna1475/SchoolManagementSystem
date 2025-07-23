import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class UniversityGUI {
    // Lists to store data
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Teacher> teachers = new ArrayList<>();
    private static ArrayList<Course> courses = new ArrayList<>();

    public static void main(String[] args) {
        // Main JFrame
        JFrame mainFrame = new JFrame("University Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        // Buttons for CRUD operations
        JButton studentButton = new JButton("Manage Students");
        JButton teacherButton = new JButton("Manage Teachers");
        JButton courseButton = new JButton("Manage Courses");
        JButton saveButton = new JButton("Save Data");
        JButton loadButton = new JButton("Load Data");

        // Panel for Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        buttonPanel.add(studentButton);
        buttonPanel.add(teacherButton);
        buttonPanel.add(courseButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        mainFrame.add(buttonPanel, BorderLayout.NORTH);

        // Event Listeners for Buttons
        studentButton.addActionListener(e -> manageStudents());
        teacherButton.addActionListener(e -> manageTeachers());
        courseButton.addActionListener(e -> manageCourses());
        saveButton.addActionListener(e -> saveData());
        loadButton.addActionListener(e -> loadData());

        // Show the frame
        mainFrame.setVisible(true);
    }

    private static void manageStudents() {
        JFrame studentFrame = new JFrame("Manage Students");
        studentFrame.setSize(600, 400);

        // Table for displaying students
        String[] columnNames = { "Student ID", "Name", "Age", "CGPA" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable studentTable = new JTable(tableModel);

        // Populate table
        for (Student student : students) {
            tableModel.addRow(
                    new Object[] { student.getStudentID(), student.getName(), student.getAge(), student.getCgpa() });
        }

        JScrollPane scrollPane = new JScrollPane(studentTable);
        studentFrame.add(scrollPane, BorderLayout.CENTER);

        // Panel for CRUD Buttons
        JPanel crudPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        crudPanel.add(addButton);
        crudPanel.add(updateButton);
        crudPanel.add(deleteButton);
        studentFrame.add(crudPanel, BorderLayout.SOUTH);

        // Add Student
        addButton.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();
            JTextField cgpaField = new JTextField();

            Object[] fields = {
                    "Student ID:", idField,
                    "Name:", nameField,
                    "Age:", ageField,
                    "CGPA:", cgpaField
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Add Student", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String id = idField.getText().trim();
                    String name = nameField.getText().trim();
                    int age = Integer.parseInt(ageField.getText().trim());
                    double cgpa = Double.parseDouble(cgpaField.getText().trim());

                    if (id.isEmpty() || name.isEmpty()) {
                        JOptionPane.showMessageDialog(studentFrame, "Error: ID and Name cannot be empty.");
                        return;
                    }

                    Student newStudent = new Student(name, age, id, "Unknown", new ArrayList<>(), cgpa, new Date());
                    students.add(newStudent);
                    tableModel.addRow(new Object[] { id, name, age, cgpa });
                    JOptionPane.showMessageDialog(studentFrame, "Student added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(studentFrame,
                            "Error: Invalid input for Age or CGPA. Please enter valid numbers.");
                }
            }
        });

        // Update Student
        updateButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(studentFrame, "Please select a student to update.");
                return;
            }

            Student selectedStudent = students.get(selectedRow);

            JTextField idField = new JTextField(selectedStudent.getStudentID());
            JTextField nameField = new JTextField(selectedStudent.getName());
            JTextField ageField = new JTextField(String.valueOf(selectedStudent.getAge()));
            JTextField cgpaField = new JTextField(String.valueOf(selectedStudent.getCgpa()));

            Object[] fields = {
                    "Student ID:", idField,
                    "Name:", nameField,
                    "Age:", ageField,
                    "CGPA:", cgpaField
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Update Student", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String id = idField.getText().trim();
                    String name = nameField.getText().trim();
                    int age = Integer.parseInt(ageField.getText().trim());
                    double cgpa = Double.parseDouble(cgpaField.getText().trim());

                    if (id.isEmpty() || name.isEmpty()) {
                        JOptionPane.showMessageDialog(studentFrame, "Error: ID and Name cannot be empty.");
                        return;
                    }

                    selectedStudent.setStudentID(id);
                    selectedStudent.setName(name);
                    selectedStudent.setAge(age);
                    selectedStudent.setCgpa(cgpa);

                    tableModel.setValueAt(id, selectedRow, 0);
                    tableModel.setValueAt(name, selectedRow, 1);
                    tableModel.setValueAt(age, selectedRow, 2);
                    tableModel.setValueAt(cgpa, selectedRow, 3);

                    JOptionPane.showMessageDialog(studentFrame, "Student updated successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(studentFrame,
                            "Error: Invalid input for Age or CGPA. Please enter valid numbers.");
                }
            }
        });

        // Delete Student
        deleteButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(studentFrame, "Please select a student to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(studentFrame, "Are you sure you want to delete this student?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                students.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(studentFrame, "Student deleted successfully!");
            }
        });

        studentFrame.setVisible(true);
    }

    private static void manageTeachers() {
        JFrame teacherFrame = new JFrame("Manage Teachers");
        teacherFrame.setSize(600, 400);

        // Table for displaying teachers
        String[] columnNames = { "Teacher ID", "Name", "Age", "Specialization" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable teacherTable = new JTable(tableModel);

        // Populate table
        for (Teacher teacher : teachers) {
            tableModel.addRow(new Object[] { teacher.getTeacherID(), teacher.getName(), teacher.getAge(),
                    teacher.getSpecialization() });
        }

        JScrollPane scrollPane = new JScrollPane(teacherTable);
        teacherFrame.add(scrollPane, BorderLayout.CENTER);

        // Panel for CRUD Buttons
        JPanel crudPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        crudPanel.add(addButton);
        crudPanel.add(updateButton);
        crudPanel.add(deleteButton);
        teacherFrame.add(crudPanel, BorderLayout.SOUTH);

        // Add Teacher
        addButton.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();
            JTextField specializationField = new JTextField();

            Object[] fields = {
                    "Teacher ID:", idField,
                    "Name:", nameField,
                    "Age:", ageField,
                    "Specialization:", specializationField
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Add Teacher", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String name = nameField.getText().trim();
                    int age = Integer.parseInt(ageField.getText().trim());
                    String specialization = specializationField.getText().trim();

                    if (name.isEmpty() || specialization.isEmpty()) {
                        JOptionPane.showMessageDialog(teacherFrame, "Error: Name and Specialization cannot be empty.");
                        return;
                    }

                    Teacher newTeacher = new Teacher(id, name, age, specialization, 5, new Date(),
                            "unknown@example.com");
                    teachers.add(newTeacher);
                    tableModel.addRow(new Object[] { id, name, age, specialization });
                    JOptionPane.showMessageDialog(teacherFrame, "Teacher added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(teacherFrame,
                            "Error: Invalid input for ID or Age. Please enter valid numbers.");
                }
            }
        });

        // Update Teacher
        updateButton.addActionListener(e -> {
            int selectedRow = teacherTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(teacherFrame, "Please select a teacher to update.");
                return;
            }

            Teacher selectedTeacher = teachers.get(selectedRow);

            JTextField idField = new JTextField(String.valueOf(selectedTeacher.getTeacherID()));
            JTextField nameField = new JTextField(selectedTeacher.getName());
            JTextField ageField = new JTextField(String.valueOf(selectedTeacher.getAge()));
            JTextField specializationField = new JTextField(selectedTeacher.getSpecialization());

            Object[] fields = {
                    "Teacher ID:", idField,
                    "Name:", nameField,
                    "Age:", ageField,
                    "Specialization:", specializationField
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Update Teacher", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String name = nameField.getText().trim();
                    int age = Integer.parseInt(ageField.getText().trim());
                    String specialization = specializationField.getText().trim();

                    if (name.isEmpty() || specialization.isEmpty()) {
                        JOptionPane.showMessageDialog(teacherFrame, "Error: Name and Specialization cannot be empty.");
                        return;
                    }

                    selectedTeacher.setTeacherID(id);
                    selectedTeacher.setName(name);
                    selectedTeacher.setAge(age);
                    selectedTeacher.setSpecialization(specialization);

                    tableModel.setValueAt(id, selectedRow, 0);
                    tableModel.setValueAt(name, selectedRow, 1);
                    tableModel.setValueAt(age, selectedRow, 2);
                    tableModel.setValueAt(specialization, selectedRow, 3);

                    JOptionPane.showMessageDialog(teacherFrame, "Teacher updated successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(teacherFrame,
                            "Error: Invalid input for ID or Age. Please enter valid numbers.");
                }
            }
        });

        // Delete Teacher
        deleteButton.addActionListener(e -> {
            int selectedRow = teacherTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(teacherFrame, "Please select a teacher to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(teacherFrame, "Are you sure you want to delete this teacher?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                teachers.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(teacherFrame, "Teacher deleted successfully!");
            }
        });

        teacherFrame.setVisible(true);
    }

    private static void manageCourses() {
        JFrame courseFrame = new JFrame("Manage Courses");
        courseFrame.setSize(800, 500);

        // Table for displaying courses
        String[] columnNames = { "Course ID", "Course Title", "Credit Hours", "Teacher Assigned" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable courseTable = new JTable(tableModel);

        // Populate table with existing courses
        for (Course course : courses) {
            tableModel.addRow(new Object[] {
                    course.getCourseID(),
                    course.getCourseTitle(),
                    course.getCreditHours(),
                    course.getTeacher() != null ? course.getTeacher().getName() : "None"
            });
        }

        JScrollPane scrollPane = new JScrollPane(courseTable);
        courseFrame.add(scrollPane, BorderLayout.CENTER);

        // Panel for CRUD Buttons
        JPanel crudPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        crudPanel.add(addButton);
        crudPanel.add(updateButton);
        crudPanel.add(deleteButton);
        courseFrame.add(crudPanel, BorderLayout.SOUTH);

        // Add Course
        addButton.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField titleField = new JTextField();
            JTextField creditsField = new JTextField();
            JComboBox<String> teacherComboBox = new JComboBox<>();

            // Populate combo box with available teachers
            teacherComboBox.addItem("None");
            for (Teacher teacher : teachers) {
                teacherComboBox.addItem(teacher.getName());
            }

            Object[] fields = {
                    "Course ID:", idField,
                    "Course Title:", titleField,
                    "Credit Hours:", creditsField,
                    "Teacher Assigned:", teacherComboBox
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Add Course", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String title = titleField.getText().trim();
                    int credits = Integer.parseInt(creditsField.getText().trim());
                    String teacherName = (String) teacherComboBox.getSelectedItem();

                    if (title.isEmpty()) {
                        JOptionPane.showMessageDialog(courseFrame, "Error: Course Title cannot be empty.");
                        return;
                    }

                    Teacher assignedTeacher = null;
                    if (!teacherName.equals("None")) {
                        for (Teacher teacher : teachers) {
                            if (teacher.getName().equals(teacherName)) {
                                assignedTeacher = teacher;
                                break;
                            }
                        }
                    }

                    Course newCourse = new Course(id, title, credits, assignedTeacher);
                    courses.add(newCourse);
                    tableModel.addRow(new Object[] {
                            id, title, credits, assignedTeacher != null ? assignedTeacher.getName() : "None"
                    });

                    JOptionPane.showMessageDialog(courseFrame, "Course added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(courseFrame,
                            "Error: Invalid input for Course ID or Credit Hours. Please enter valid numbers.");
                }
            }
        });

        // Update Course
        updateButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(courseFrame, "Please select a course to update.");
                return;
            }

            Course selectedCourse = courses.get(selectedRow);

            JTextField idField = new JTextField(String.valueOf(selectedCourse.getCourseID()));
            JTextField titleField = new JTextField(selectedCourse.getCourseTitle());
            JTextField creditsField = new JTextField(String.valueOf(selectedCourse.getCreditHours()));
            JComboBox<String> teacherComboBox = new JComboBox<>();

            // Populate combo box with available teachers
            teacherComboBox.addItem("None");
            for (Teacher teacher : teachers) {
                teacherComboBox.addItem(teacher.getName());
            }
            teacherComboBox.setSelectedItem(
                    selectedCourse.getTeacher() != null ? selectedCourse.getTeacher().getName() : "None");

            Object[] fields = {
                    "Course ID:", idField,
                    "Course Title:", titleField,
                    "Credit Hours:", creditsField,
                    "Teacher Assigned:", teacherComboBox
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Update Course", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String title = titleField.getText().trim();
                    int credits = Integer.parseInt(creditsField.getText().trim());
                    String teacherName = (String) teacherComboBox.getSelectedItem();

                    if (title.isEmpty()) {
                        JOptionPane.showMessageDialog(courseFrame, "Error: Course Title cannot be empty.");
                        return;
                    }

                    Teacher assignedTeacher = null;
                    if (!teacherName.equals("None")) {
                        for (Teacher teacher : teachers) {
                            if (teacher.getName().equals(teacherName)) {
                                assignedTeacher = teacher;
                                break;
                            }
                        }
                    }

                    selectedCourse.setCourseID(id);
                    selectedCourse.setCourseTitle(title);
                    selectedCourse.setCreditHours(credits);
                    selectedCourse.setTeacher(assignedTeacher);

                    tableModel.setValueAt(id, selectedRow, 0);
                    tableModel.setValueAt(title, selectedRow, 1);
                    tableModel.setValueAt(credits, selectedRow, 2);
                    tableModel.setValueAt(assignedTeacher != null ? assignedTeacher.getName() : "None", selectedRow, 3);

                    JOptionPane.showMessageDialog(courseFrame, "Course updated successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(courseFrame,
                            "Error: Invalid input for Course ID or Credit Hours. Please enter valid numbers.");
                }
            }
        });

        // Delete Course
        deleteButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(courseFrame, "Please select a course to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(courseFrame, "Are you sure you want to delete this course?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                courses.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(courseFrame, "Course deleted successfully!");
            }
        });

        courseFrame.setVisible(true);
    }

    private static void saveData() {
        try (
                FileWriter studentWriter = new FileWriter("students.txt");
                FileWriter teacherWriter = new FileWriter("teachers.txt");
                FileWriter courseWriter = new FileWriter("courses.txt")) {
            // Save Students
            for (Student student : students) {
                studentWriter.write(String.join(",",
                        student.getStudentID(),
                        student.getName(),
                        String.valueOf(student.getAge()),
                        String.valueOf(student.getCgpa())) + "\n");
            }

            // Save Teachers
            for (Teacher teacher : teachers) {
                teacherWriter.write(String.join(",",
                        String.valueOf(teacher.getTeacherID()),
                        teacher.getName(),
                        String.valueOf(teacher.getAge()),
                        teacher.getSpecialization()) + "\n");
            }

            // Save Courses
            for (Course course : courses) {
                courseWriter.write(String.join(",",
                        String.valueOf(course.getCourseID()),
                        course.getCourseTitle(),
                        String.valueOf(course.getCreditHours()),
                        course.getTeacher() != null ? course.getTeacher().getName() : "None") + "\n");
            }

            JOptionPane.showMessageDialog(null, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while saving data: " + e.getMessage());
        }
    }

    private static void loadData() {
        try (
                BufferedReader studentReader = new BufferedReader(new FileReader("students.txt"));
                BufferedReader teacherReader = new BufferedReader(new FileReader("teachers.txt"));
                BufferedReader courseReader = new BufferedReader(new FileReader("courses.txt"))) {
            // Clear existing data to avoid duplication
            students.clear();
            teachers.clear();
            courses.clear();

            // Load Students
            String studentLine;
            while ((studentLine = studentReader.readLine()) != null) {
                try {
                    String[] studentData = studentLine.split(",");
                    if (studentData.length != 4) {
                        throw new IllegalArgumentException("Invalid student data format.");
                    }
                    String id = studentData[0];
                    String name = studentData[1];
                    int age = Integer.parseInt(studentData[2].trim());
                    double cgpa = Double.parseDouble(studentData[3].trim());

                    students.add(new Student(name, age, id, "Unknown", new ArrayList<>(), cgpa, new Date()));
                } catch (Exception e) {
                    System.err.println("Skipping invalid student entry: " + studentLine);
                }
            }

            // Load Teachers
            String teacherLine;
            while ((teacherLine = teacherReader.readLine()) != null) {
                try {
                    String[] teacherData = teacherLine.split(",");
                    if (teacherData.length != 4) {
                        throw new IllegalArgumentException("Invalid teacher data format.");
                    }
                    int id = Integer.parseInt(teacherData[0].trim());
                    String name = teacherData[1];
                    int age = Integer.parseInt(teacherData[2].trim());
                    String specialization = teacherData[3];

                    teachers.add(new Teacher(id, name, age, specialization, 5, new Date(), "unknown@example.com"));
                } catch (Exception e) {
                    System.err.println("Skipping invalid teacher entry: " + teacherLine);
                }
            }

            // Load Courses
            String courseLine;
            while ((courseLine = courseReader.readLine()) != null) {
                try {
                    String[] courseData = courseLine.split(",");
                    if (courseData.length != 4) {
                        throw new IllegalArgumentException("Invalid course data format.");
                    }
                    int id = Integer.parseInt(courseData[0].trim());
                    String title = courseData[1];
                    int credits = Integer.parseInt(courseData[2].trim());
                    String teacherName = courseData[3];

                    Teacher assignedTeacher = null;
                    if (!teacherName.equals("None")) {
                        for (Teacher teacher : teachers) {
                            if (teacher.getName().equals(teacherName)) {
                                assignedTeacher = teacher;
                                break;
                            }
                        }
                    }

                    courses.add(new Course(id, title, credits, assignedTeacher));
                } catch (Exception e) {
                    System.err.println("Skipping invalid course entry: " + courseLine);
                }
            }

            JOptionPane.showMessageDialog(null, "Data loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while loading data: " + e.getMessage());
        }
    }

}
