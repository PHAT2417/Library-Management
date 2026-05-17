
/*
 * Created: Grace Ho (Modified by Pooja Ragala)
 * Last updated: 5/13/2026
 * Book class that manages the following attributes:
 	* Title
 	* Author
 	* ISBN
 	* Publication year
 	* Availability status
 */

public class Book {
	
	// Book attributes
	String title; // title of Book
	String authorFirst; // author's first name of Book
	String authorLast; // author's last name of Book
	String ISBN; // unique # Id of Book
	int year; // publication year of Book
	boolean status; // availability status of Book
	
	/**
	 * Create book with certain attributes
	 * @param title
	 * @param author
	 * @param ISBN
	 * @param year
	 */
	public Book(String title, String authorFirst, String authorLast, String ISBN, int year) {
		this.title = title;
		this.authorFirst = authorFirst;
		this.authorLast = authorLast;
		this.ISBN = ISBN;
		this.year = year;
		this.status = true; // auto set availability to available
	}
	
	/**
	 * Change status as needed
	 * @param status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	/*
	 * Getter methods for all attributes
	 */
	public String getTitle() {
		return this.title;
	}
	
	public String getAuthor() {
		
		return this.authorLast + ", " + this.authorFirst;
	}
	
	public String getISBN() {
		return this.ISBN;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
	/**
	 * Convert book information into file format
	 * @return formatted book string
	 */
	public String toString() {
		return getTitle() + ", by " + getAuthor() + ", " + getISBN() + 
				", " + getYear();
	}

}
