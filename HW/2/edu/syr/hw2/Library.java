package edu.syr.hw2;

import java.util.ArrayList;
import java.util.List;
import edu.syr.hw2.Book;

public class Library {
    // Catalog variable is made private in order to restrict the users from deleting the titles from catalog
    private ArrayList<Book> catalog = new ArrayList<Book>();

    public void add (Book bookObj) {
        this.catalog.add(bookObj);
    }

    // No static keyword, as the catalog is an attibute of the Library object.
    // Every Library object needs to search in it's own catalog.
    public ArrayList<Book> search (Book b) {

        ArrayList<Book> result = new ArrayList<Book>();
        // Loop through all titles in the catalog
        for (Book book : catalog) {
            // Exact match case
            if (book.equals(b)) {
                result.add(0, book);
            }

            // Partial match case
            else if (book.containsBook(b)) {
                result.add(book);
            }
        }
        return result;
    }

    public static void main (String[] args) {
        Library lib = new Library();
        Book b1 = new Book("Author1", "Title1", "Publisher1", "ISBN1", 2000);
        Book b2 = new Book("Author2", "Title2", "Publisher1", "ISBN2", 2001);
        Book b3 = new Book("Author1", "Title3", "Publisher2", "ISBN3", 2000);
        lib.add(b1);
        lib.add(b2);
        lib.add(b3);
        Book b4 = new Book("Author2", "Title2", "Publisher1", "ISBN2", 2001); // equals case
        Book b5 = new Book("Author1", "", "", "", 0); // contains author case
        Book b6 = new Book("", "Title1", "", "", 0); // contains title case
        Book b7 = new Book("", "", "Publisher1", "", 0); // contains publisher case
        Book b8 = new Book("", "", "", "ISBN1", 0); // contains isbn case
        Book b9 = new Book("", "", "", "", 2000); // contains year case
        Book b10 = new Book("Author", "", "", "", 0); // Multiple books with similar Author names
        Book b11 = new Book("", "Title", "", "", 0); // title
        Book b12 = new Book("", "", "Publisher", "", 0); // publisher
        Book b13 = new Book("", "", "", "ISBN", 0);
        
        System.out.println(lib.search(b4));
        System.out.println();
        System.out.println(lib.search(b5));
        System.out.println();
        System.out.println(lib.search(b6));
        System.out.println();
        System.out.println(lib.search(b7));
        System.out.println();
        System.out.println(lib.search(b8));
        System.out.println();
        System.out.println(lib.search(b9));
        System.out.println();
        System.out.println(lib.search(b10));
        System.out.println();
        System.out.println(lib.search(b11));
        System.out.println();
        System.out.println(lib.search(b12));
        System.out.println();
        System.out.println(lib.search(b13));
        System.out.println();
    }
}