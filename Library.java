import java.util.HashMap;
import java.util.List;
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

    }

    public void saveToFile(String filename) {

    }
}