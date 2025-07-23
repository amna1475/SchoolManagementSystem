import java.util.ArrayList;

public class Repository<T> {
    private ArrayList<T> items;

    // Constructor
    public Repository() {
        items = new ArrayList<>();
    }

    // Method to add an item to the repository
    public void add(T item) {
        try {
            if (item == null) {
                throw new IllegalArgumentException("Cannot add null items to the repository.");
            }
            items.add(item);
            System.out.println(item + " added to the repository.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to remove an item from the repository
    public void remove(T item) {
        try {
            if (item == null) {
                throw new IllegalArgumentException("Cannot remove null items from the repository.");
            }

            if (items.isEmpty()) {
                System.out.println("Repository is empty. No items to remove.");
                return;
            }

            if (!items.remove(item)) {
                System.out.println(item + " not found in the repository.");
            } else {
                System.out.println(item + " removed from the repository.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred during removal: " + e.getMessage());
        }
    }

    // Method to retrieve all items (returns the original list)
    public ArrayList<T> getAll() {
        try {
            if (items == null) {
                throw new IllegalStateException("The repository is not initialized properly.");
            }
            return items; // Exposing the original list directly
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list if there's an issue
        }
    }
}
