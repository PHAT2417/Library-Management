/*
* Grace Ho, Pooja Ragala, Nathan Tam, Xuan Phat Tran, and Shedrack Umegboh
* Library.java is able to store books. It stores books in 
* 3 different HashMaps that allow for future searches by
* author, title, and ISBN. This program also allows a user to
* see the first 20 books in the library, see available books,
* check out books, add books, and remove books.
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Library {

    // Fields
	// All keys are not case sensitive
    private HashMap<String, Book> booksByISBN; // Key: ISBN, Value: Book
    private HashMap<String, ArrayList<Book>> booksByTitle; // Key: Title, Value: ArrayList of Books
    private HashMap<String, ArrayList<Book>> booksByAuthor; // Key: Author, Value: ArrayList of Books

    /**
     * Constructor
     */
    public Library() {
		// initialize HashMaps
        booksByISBN = new HashMap<>();
        booksByTitle = new HashMap<>();
        booksByAuthor = new HashMap<>();
    }

    //Methods to Manage Books
    
    /**
     * Add book to the Maps
     * Big O Analysis 
	 	Average Case: O(1)
			Basic appending, without resizing, does not require traversal
	 	Worst Case: O(n)
			Appending with resizing of ArrayList, requires traversal to copy elements
     * @param book - object being added
     */
    public void addBook(Book book) {
        // Add book to booksByISBN
        booksByISBN.put(book.getISBN(), book);

        // Add book to booksByTitle
        String title = book.getTitle().toLowerCase();
		// Check if already in HashMap
        if (!booksByTitle.containsKey(title)) {
			// Initialize ArrayList
            booksByTitle.put(title, new ArrayList<Book>());
        }
        booksByTitle.get(title).add(book);

        // Add book to booksByAuthor
        String author = book.getAuthor().toLowerCase();
		// Check if already in HashMap
        if (!booksByAuthor.containsKey(author)) {
			// Initialize ArrayList
            booksByAuthor.put(author, new ArrayList<>());
        }
        booksByAuthor.get(author).add(book);
    }

    /**
     * Remove book from Maps
     * @param isbn - key to identify book
     */
    public void removeBook(String isbn) {
		// Check is Book is in the library first
    	if(findBookByISBN(isbn) != null) {
    		// Find book by ISBN
    		Book book = findBookByISBN(isbn);
    		
    		// Remove from all maps
    		booksByISBN.remove(isbn);
    		booksByAuthor.remove(book.getAuthor().toLowerCase()));
    		booksByTitle.remove(book.getTitle().toLowerCase()));
    		System.out.println("Successfully removed " + isbn + "\n");
    	}
    	else {
			// Error message
    		System.out.println(isbn + " is not in the library\n");
    	}
    }

    /**
     * Use ISBN to find books, will access ISBN map
      * Big O Analysis - Average Case: O(n)
	  	Traverses ArrayList of n size to find book based on given ISBN
     * @param isbn - key to find
     * @return book found
     */
    public Book findBookByISBN(String isbn) {
        return booksByISBN.get(isbn);
    }

    /**
     * Find book(s) using a title. Can share title, but have different
     * authors
      * Big O Analysis - Average Case: O(n)
	  	Traverses ArrayList of n size to find book based on given title
     * @param title - key to find
     * @return list of books with that title
     */
    public ArrayList<Book> findBooksByTitle(String title) {
        return booksByTitle.get(title.toLowerCase());
    }

    /**
     * Find book(s) using the author name. Can share title, but have 
     * different titles
      * Big O Analysis - Average Case: O(n)
	  	Traverses ArrayList of n size to find book based on given author
     * @param author - key to find
     * @return list of books with that author
     */
    public List<Book> findBooksByAuthor(String author) {
        return booksByAuthor.get(author.toLowerCase());
    }


    //Borrowing and Returning Books
    /**
     * Borrow a book from the library, update status of book (false)
     * Big O Analysis - Average Case: O(n)
	 	Traverses ArrayList of n size to find if book exists, and if does, marks as unavailable and provides book
     * @param isbn - key to find
     * @return true/false, whether borrow succeeded
     */
    public boolean borrowBook(String isbn) {
		Book book = findBookByISBN(isbn);

		// Check if book was found
		if (book == null) {
			System.out.println("Book with ISBN " + isbn + " not found.\n");
			return false;
		}

		// Check if book is available
		if (!book.getStatus()) {
			System.out.println(book.getTitle() + " is already borrowed.\n");
			return false;
		}

		// Update book status
		book.setStatus(false);
		System.out.println("Successfully borrowed: " + book.getTitle() + "\n");
		return true;
    }

    /**
     * Update status of book (true)
      * Big O Analysis - Average Case: O(n)
	  	Traverses ArrayList of n size to find if book exists, and if does, marks as available again
     * @param isbn - key to find
     * @return true/false, whether return succeeded
     */
    public boolean returnBook(String isbn) {
		Book book = findBookByISBN(isbn);

		// Check if book was found
		if (book == null) {
			System.out.println("Book with ISBN " + isbn + " not found.\n");
			return false;
		}

		// Check if book is available
		if (book.getStatus()) { 
			System.out.println(book.getTitle() + " is not currently "
					+ "borrowed.\n");
			return false;
		}

		// Update book status
		book.setStatus(true);
		System.out.println("Successfully returned: " + book.getTitle() 
					+ "\n");
		return true;

    }


    // Display and Reporting
    /**
     * Display first 20 books.
      * Big O Analysis - Average Case: O(n)
	  	Traverses ArrayList of n size to display all existing books
     */
    public void displayAllBooks() {
		// Check if Library has ANY books
		if (booksByISBN.isEmpty()) {
			System.out.println("The Library has no books.\n");
			return;
		}

		// Header
		System.out.println("=== All Books (first 20 shown) ===");

		int count = 0; // track books displayed

		for (Book book : booksByISBN.values()) {

			if (count >= 20) {
				break;
			}

			System.out.println((count + 1) + ". " + book.toString()
					+ " [" + (book.getStatus() ? "Available" : "Borrowed") 
					+ "]");
			count++; // increment count
								

    	}
		System.out.println("Total books in library: "
				+ booksByISBN.size() + "\n");
	}

    /**
     * Display all available (status = True) books
      * Big O Analysis - Average Case: O(n)
	  	Traverses ArrayList of n size to find if book exists, and if does AND it is available, displays it
     */
    public void displayAllAvailableBooks() {
		System.out.println("=== Available Books ===");

		int count = 0; // track books displayed

		for (Book book : booksByISBN.values()) {
			if (book.getStatus()) {
				count++; // increment count
				System.out.println(count + ". " + book.toString());
			}
		}

		if (count == 0) {
			System.out.println("No books are currently available.");
		} else {
			System.out.println("Total available: " + count + "\n");
		}
	
    }

   /**
    * Load books from a file and add to our Maps
     * Big O Analysis - Average Case: O(n)
	 	Traverses a given file and for each of the n lines, splits it to make it a book in the library
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
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {

            int saved = 0;

            for (Book book : booksByISBN.values()) {
                // Format: "Title, by LastName, FirstName, ISBN, Year"
                // getAuthor() returns "LastName,FirstName" (no space after comma)
                // so we split and rejoin with proper spacing
                String[] authorParts = book.getAuthor().split(",", 2);
                String lastName  = authorParts[0].trim();
                String firstName = authorParts.length > 1 ? authorParts[1].trim() : "";

                String line = book.getTitle() + ", by " +
                            lastName + ", " + firstName + ", " +
                            book.getISBN() + ", " + book.getYear();

                bw.write(line);
                bw.newLine();
                saved++;
            }

            System.out.println("\nSaved to file: " + filename);
            System.out.println("  Successfully saved: " + saved 
            		+ " books\n");

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
	}
    
    /**
     * Driver / main method — interactive menu that tests all Library methods.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Library Management System!");
        System.out.println("------------------------------------------");

        // Load file on startup
        System.out.print("Enter filename to load (e.g. book_donation.txt), "
        		+ "or press Enter to skip: ");
        String filename = scanner.nextLine().trim();
        if (!filename.isEmpty()) {
            library.loadFromFile(filename);
        } else {
            System.out.println("No file loaded. Starting with an "
            		+ "empty library.\n");
        }

        // Interactive menu loop
        boolean running = true;
        while (running) {
            System.out.println("\n========== LIBRARY MANAGEMENT SYSTEM ==="
            		+ "=======");
            System.out.println(" 1. Display all books (first 20)");
            System.out.println(" 2. Display all available books");
            System.out.println(" 3. Search book by ISBN");
            System.out.println(" 4. Search books by title");
            System.out.println(" 5. Search books by author");
            System.out.println(" 6. Add a new book");
            System.out.println(" 7. Remove a book");
            System.out.println(" 8. Borrow a book");
            System.out.println(" 9. Return a book");
            System.out.println("10. Load books from file");
            System.out.println("11. Save books to file");
            System.out.println(" 0. Exit");
            System.out.println("=========================================="
            		+ "======");
            System.out.print("Enter your choice: ");

            String choiceStr = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number "
                		+ "between 0 and 11.\n");
                continue;
            }

            System.out.println("----------------------------------------");

            switch (choice) {

                case 1:
                    library.displayAllBooks();
                    break;

                case 2:
                    library.displayAllAvailableBooks();
                    break;

                case 3:
                    System.out.print("Enter ISBN: ");
                    String isbnSearch = scanner.nextLine().trim();
                    if (isbnSearch.isEmpty()) {
                        System.out.println("ISBN cannot be empty.\n");
                        break;
                    }
                    Book found = library.findBookByISBN(isbnSearch);
                    if (found != null) {
                        System.out.println("Book found:");
                        System.out.println("  " + found.toString() + " [" 
                        + (found.getStatus() ? "Available" : "Borrowed") + "]\n");
                    } else {
                        System.out.println("No book found with ISBN: " 
                        		+ isbnSearch + "\n");
                    }
                    break;

                case 4:
                    System.out.print("Enter title: ");
                    String titleSearch = scanner.nextLine().trim();
                    if (titleSearch.isEmpty()) {
                        System.out.println("Title cannot be empty.\n");
                        break;
                    }
                    ArrayList<Book> byTitle = library.findBooksByTitle(titleSearch);
                    if (byTitle != null && !byTitle.isEmpty()) {
                        System.out.println("Books found (" + byTitle.size() + "):");
                        int ti = 1;
                        for (Book b : byTitle) {
                            System.out.println("  " + ti++ + ". " 
                            		+ b.toString()  + " [" 
                            		+ (b.getStatus() ? "Available" : "Borrowed") + "]");
                        }
                        System.out.println();
                    } else {
                        System.out.println("No books found with title: \"" 
                        		+ titleSearch + "\"\n");
                    }
                    break;

                case 5:
                    System.out.println("Enter author name as: Last, First");
                    System.out.print("Author: ");
                    String authorSearch = scanner.nextLine().trim();
                    if (authorSearch.isEmpty()) {
                        System.out.println("Author name cannot be empty.\n");
                        break;
                    }
                    List<Book> byAuthor = library.findBooksByAuthor(authorSearch);
                    if (byAuthor != null && !byAuthor.isEmpty()) {
                        System.out.println("Books by \"" + authorSearch 
                        		+ "\" (" + byAuthor.size() + "):");
                        int ai = 1;
                        for (Book b : byAuthor) {
                            System.out.println("  " + ai++ + ". " 
                            		+ b.toString() + " [" 
                            		+ (b.getStatus() ? "Available" : "Borrowed") 
                            		+ "]");
                        }
                        System.out.println();
                    } else {
                        System.out.println("No books found for author: \"" 
                        		+ authorSearch + "\"\n");
                    }
                    break;

                case 6:
                    System.out.println("--- Add New Book ---");
                    System.out.print("Title: ");
                    String newTitle = scanner.nextLine().trim();
                    System.out.print("Author first name: ");
                    String newFirst = scanner.nextLine().trim();
                    System.out.print("Author last name: ");
                    String newLast = scanner.nextLine().trim();
                    System.out.print("ISBN: ");
                    String newISBN = scanner.nextLine().trim();
                    System.out.print("Publication year: ");
                    String yearStr = scanner.nextLine().trim();

                    if (newTitle.isEmpty() || newFirst.isEmpty() 
                    		|| newLast.isEmpty() || newISBN.isEmpty() 
                    		|| yearStr.isEmpty()) {
                        System.out.println("All fields are required. "
                        		+ "Book not added.\n");
                        break;
                    }
                    if (library.findBookByISBN(newISBN) != null) {
                        System.out.println("A book with ISBN " + newISBN 
                        		+ " already exists.\n");
                        break;
                    }
                    try {
                        int newYear = Integer.parseInt(yearStr);
                        library.addBook(new Book(newTitle, newFirst, 
                        		newLast, newISBN, newYear));
                        System.out.println("Successfully added: \"" 
                        		+ newTitle + "\"\n");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid year. Book not added.\n");
                    }
                    break;

                case 7:
                    System.out.print("Enter ISBN of book to remove: ");
                    String removeISBN = scanner.nextLine().trim();
                    if (removeISBN.isEmpty()) {
                        System.out.println("ISBN cannot be empty.\n");
                        break;
                    }
                    library.removeBook(removeISBN);
                    break;

                case 8:
                    System.out.print("Enter ISBN of book to borrow: ");
                    String borrowISBN = scanner.nextLine().trim();
                    if (borrowISBN.isEmpty()) {
                        System.out.println("ISBN cannot be empty.\n");
                        break;
                    }
                    library.borrowBook(borrowISBN);
                    break;

                case 9:
                    System.out.print("Enter ISBN of book to return: ");
                    String returnISBN = scanner.nextLine().trim();
                    if (returnISBN.isEmpty()) {
                        System.out.println("ISBN cannot be empty.\n");
                        break;
                    }
                    library.returnBook(returnISBN);
                    break;

                case 10:
                    System.out.print("Enter filename to load: ");
                    String loadFile = scanner.nextLine().trim();
                    if (loadFile.isEmpty()) {
                        System.out.println("Filename cannot be empty.\n");
                        break;
                    }
                    library.loadFromFile(loadFile);
                    break;

                case 11:
                    System.out.print("Enter filename to save to: ");
                    String saveFile = scanner.nextLine().trim();
                    if (saveFile.isEmpty()) {
                        System.out.println("Filename cannot be empty.\n");
                        break;
                    }
                    library.saveToFile(saveFile);
                    break;

                case 0:
                    System.out.println("Thank you for using the Library "
                    		+ "Management System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a "
                    		+ "number between 0 and 11.\n");
                    break;
            }
        }

        scanner.close();
    }
}
