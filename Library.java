import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Library {

    //Fields
    private HashMap<String, Book> booksByISBN;
    private HashMap<String, List<Book>> booksByTitle;
    private HashMap<String, List<Book>> booksByAuthor;

    //Constructor
    public Library() {
        booksByISBN = new HashMap<>();
        booksByTitle = new HashMap<>();
        booksByAuthor = new HashMap<>();
    }

    //Methods to Manage Books
    public void addBook(Book book) {
        // Add book to booksByISBN
        booksByISBN.put(book.getISBN(), book);

        // Add book to booksByTitle
        String title = book.getTitle().toLowerCase();
        if (!booksByTitle.containsKey(title)) {
            booksByTitle.put(title, new ArrayList<>());
        }
        booksByTitle.get(title).add(book);

        // Add book to booksByAuthor
        String author = book.getAuthor().toLowerCase();
        if (!booksByAuthor.containsKey(author)) {
            booksByAuthor.put(author, new ArrayList<>());
        }
        booksByAuthor.get(author).add(book);
    }

    public void removeBook(String isbn) {

    }

    public Book findBookByISBN(String isbn) {
        return null;
    }

    public List<Book> findBooksByTitle(String title) {
        return null;
    }

    public List<Book> findBooksByAuthor(String author) {
        return null;
    }


    //Borrowing and Returning Books
    public boolean borrowBook(String isbn) {
        return false;
    }

    public boolean returnBook(String isbn) {
        return false;
    }


    //Display and Reporting
    public void displayAllBooks() {

    }

    public void displayAllAvailableBooks() {

    }

    //File I/O
   public void loadFromFile(String filename) {
        int loaded = 0;
        int failed = 0;
 
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
 
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
 
                try {
                    // Split at ", by " to separate title from author+isbn+year
                    String[] splitBy = line.split(", by ", 2);
                    if (splitBy.length < 2) {
                        System.out.println("Skipping malformed line: " + line);
                        failed++;
                        continue;
                    }
 
                    String title = splitBy[0].trim();
 
                    // rest = "LastName, FirstName[, extras], ISBN, Year"
                    String[] rest = splitBy[1].split(", ");
 
                    // Year is always last, ISBN is always second to last
                    int year = Integer.parseInt(rest[rest.length - 1].trim());
                    String isbn = rest[rest.length - 2].trim();
 
                    // Author: first two tokens are Last, First
                    String lastName  = rest[0].trim();
                    String firstName = rest[1].trim();
 
                    addBook(new Book(title, firstName, lastName, isbn, year));
                    loaded++;
 
                } catch (Exception e) {
                    System.out.println("Error parsing line: " + line);
                    failed++;
                }
            }
 
            System.out.println("\nFile loaded: " + filename);
            System.out.println("  Successfully loaded : " + loaded + " books");
            if (failed > 0) {
                System.out.println("  Failed to parse     : " + failed + " lines");
            }
 
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveToFile(String filename) {

    }

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
 
        // Step 1: Load file
        System.out.print("Enter filename to load (e.g. book_donation.txt): ");
        String filename = scanner.nextLine().trim();
        library.loadFromFile(filename);
    }
}