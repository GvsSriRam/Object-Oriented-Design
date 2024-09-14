package edu.syr.hw2;

import java.util.ArrayList;
import java.util.List;

public class Library {
    // Catalog variable is made private in order to restrict the users from deleting the titles from catalog
    private List<Book> catalog = new ArrayList<>();

    public void add (Book bookObj) {
        this.catalog.add(bookObj);
    }

    // The search method is not static, as it needs to access the catalog attribute of the Library object.
    // The search method is public, as it needs to be accessed by the users of the Library class.
    // Returns an ArrayList of Book objects, as the search can return multiple books.
    public List<Book> search (Book b) {

        // Initialize the result ArrayList
        ArrayList<Book> result = new ArrayList<>();
        
        // Loop through all titles in the catalog
        for (Book book : catalog) {
            // Exact match case
            if (book.equals(b)) {
                result.add(0, book);
//                System.out.println("equals\n");
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
        lib.add(new Book("Sri Ram", "My amazing life", "Syracuse Publications", "ISBNSYR1", 2024));
        lib.add(new Book("Vennela", "Vennela's Life", "Syracuse Publications", "ISBNSYR2", 2023));

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
        Book b14 = new Book("Author1", "Title2", "", "", 0);
        Book b15 = new Book("", "life", "", "", 0);
        Book b16 = new Book("Vennela", "life", "", "", 0);
        Book b17 = new Book("Sri Ram", "Vennela's life", "", "", 0);
        Book b18 = new Book("", "Vennelas life", "", "", 0);
        
        System.out.println(lib.search(b4)); // [b2]
        System.out.println();
        System.out.println(lib.search(b5)); // [b1, b3]
        System.out.println();
        System.out.println(lib.search(b6)); // [b1]
        System.out.println();
        System.out.println(lib.search(b7)); // [b1, b2]
        System.out.println();
        System.out.println(lib.search(b8)); // [b1]
        System.out.println();
        System.out.println(lib.search(b9)); // [b1, b3]
        System.out.println();
        System.out.println(lib.search(b10)); // [b1, b2, b3]
        System.out.println();
        System.out.println(lib.search(b11)); // [b1, b2, b3]
        System.out.println();
        System.out.println(lib.search(b12)); // [b1, b2, b3]
        System.out.println();
        System.out.println(lib.search(b13)); // []
        System.out.println();
        System.out.println(lib.search(b14)); // []
        System.out.println();
        System.out.println(lib.search(b15)); // [Sri Ram & Vennela's books]
        System.out.println();
        System.out.println(lib.search(b16)); // [Vennela's life]
        System.out.println();
        System.out.println(lib.search(b17)); // []
        System.out.println();
        System.out.println(lib.search(b18)); // []
        System.out.println();
    }
}