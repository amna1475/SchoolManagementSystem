public class Date {
    // Attributes
    private int day;
    private int month;
    private int year;

    // Default constructor
    public Date() {
        this.day = 1;
        this.month = 1;
        this.year = 2000; 
    }

    // Parameterized constructor with exception handling
    public Date(int day, int month, int year) {
        try {
            setMonth(month); // Validate month
            setYear(year);   // Set year before validating day
            setDay(day);     // Validate day based on month and year
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            // Set default values in case of an exception
            this.day = 1;
            this.month = 1;
            this.year = 2000;
        }
    }

    // Helper Method to Validate Day
    private boolean isValidDay(int day, int month, int year) {
        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Check for leap year and adjust February's days
        if (month == 2 && isLeapYear(year)) {
            daysInMonth[2] = 29;
        }

        return day >= 1 && day <= daysInMonth[month];
    }

    // Helper Method to Check Leap Year
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // Getters
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // Setters with error handling
    public void setDay(int day) {
        if (!isValidDay(day, this.month, this.year)) {
            throw new IllegalArgumentException("Invalid day for the given month and year: " + day);
        }
        this.day = day;
    }

    public void setMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month: " + month + ". Must be between 1 and 12.");
        }
        this.month = month;
    }

    public void setYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Invalid year: " + year + ". Must be positive.");
        }
        this.year = year;
    }

    // toString() method to return the date in a string format
    @Override
    public String toString() {
        return day + "-" + month + "-" + year;
    }
}
