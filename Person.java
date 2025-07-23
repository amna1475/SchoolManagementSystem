public class Person {
    // Attributes
    private String name;
    private int age;
    private Date dateOfBirth;  
    private String email; 

    // Default constructor
    public Person() {
        this.dateOfBirth = new Date();  
        this.email = "unknown@example.com";
    }

    // Parameterized constructor with validation
    public Person(String name, int age, Date dateOfBirth, String email) {
        try {
            setName(name);
            setAge(age);
            setDateOfBirth(dateOfBirth);
            setEmail(email);
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating Person: " + e.getMessage());
            this.name = "Unknown";
            this.age = 0;
            this.dateOfBirth = new Date(); // Default date
            this.email = "unknown@example.com";
        }
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    // Setter methods with error handling
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must bengreater than 0.");
        }
        this.age = age;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of Birth cannot be null.");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        this.email = email;
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    // Overridden toString() method to return a string representation of the Person object
    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Date of Birth: " 
                + dateOfBirth.getDay() + "-" + dateOfBirth.getMonth() + "-" + dateOfBirth.getYear() 
                + ", Email: " + email;
    }
}
