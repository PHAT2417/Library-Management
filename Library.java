import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Library {

    //Fields
    private HashMap<String, Book> booksByISBN;
    private HashMap<String, ArrayList<Book>> booksByTitle;
    private HashMap<String, ArrayList<Book>> booksByAuthor;

    /**
     * Constructor
     */
    public Library() {
        booksByISBN = new HashMap<>();
        booksByTitle = new HashMap<>();
        booksByAuthor = new HashMap<>();
    }

    //Methods to Manage Books
    
    /**
     * Add book to the Maps
     * @param book - object being added
     */
    public void addBook(Book book) {
        // Add book to booksByISBN
        booksByISBN.put(book.getISBN(), book);

        // Add book to booksByTitle
        String title = book.getTitle().toLowerCase();
        if (!booksByTitle.containsKey(title)) {
            booksByTitle.put(title, new ArrayList<Book>());
        }
        booksByTitle.get(title).add(book);

        // Add book to booksByAuthor
        String author = book.getAuthor().toLowerCase();
        if (!booksByAuthor.containsKey(author)) {
            booksByAuthor.put(author, new ArrayList<>());
        }
        booksByAuthor.get(author).add(book);
    }

    /**
     * Remove book from Maps
     * @param isbn - key to identify book
     */
    public void removeBook(String isbn) {
    	if(findBookByISBN(isbn) != null) {
    		// Find book by ISBN
    		Book book = findBookByISBN(isbn);
    		
    		// Remove from all maps
    		booksByISBN.remove(isbn);
    		booksByAuthor.remove(book.getAuthor());
    		booksByTitle.remove(book.getTitle());
    		System.out.println("Successfully removed " + isbn + "\n");
    	}
    	else {
    		System.out.println(isbn + " is not in the library\n");
    	}
    }

    /**
     * Use ISBN to find books, will access ISBN map
     * @param isbn - key to find
     * @return book found
     */
    public Book findBookByISBN(String isbn) {
        return booksByISBN.get(isbn);
    }

    /**
     * Find book(s) using a title. Can share title, but have different
     * authors
     * @param title - key to find
     * @return list of books with that title
     */
    public ArrayList<Book> findBooksByTitle(String title) {
        return booksByTitle.get(title.toLowerCase());
    }

    /**
     * Find book(s) using the author name. Can share title, but have 
     * different titles
     * @param author - key to find
     * @return list of books with that author
     */
    public List<Book> findBooksByAuthor(String author) {
        return booksByAuthor.get(author.toLowerCase());
    }


    //Borrowing and Returning Books
    /**
     * Borrow a book from the library, update status of book (false)
     * @param isbn - key to find
     * @return true/false, whether borrow succeeded
     */
    public boolean borrowBook(String isbn) {
        return false;
    }

    /**
     * Update status of book (true)
     * @param isbn - key to find
     * @return true/false, whether return succeeded
     */
    public boolean returnBook(String isbn) {
        return false;
    }


    //Display and Reporting
    /**
     * Display first 20 books.
     */
    public void displayAllBooks() {

    }

    /**
     * Display all available (status = True) books
     */
    public void displayAllAvailableBooks() {

    }

   /**
    * Load books from a file and add to our Maps
    * @param filename
    */
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
            System.out.println("  Successfully loaded : " + loaded + 
            		" books" + "\n");
            if (failed > 0) {
                System.out.println("  Failed to parse     : " + failed + 
                		" lines\n");
            }
 
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

   /**
    * Save all book information to a new file
    * @param filename - what the file will be named
    */
	public void saveToFile(String filename) {
	
	}
    
    /**
     * Driver program to test Library and Book methods
     * @param args
     */
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
 
        // Step 1: Load file
        System.out.print("Enter filename to load (e.g. book_donation.txt): ");
        String filename = scanner.nextLine().trim();
        library.loadFromFile(filename);
    }
}
