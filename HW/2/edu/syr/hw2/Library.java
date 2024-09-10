import edu.syr.hw2.Book;

package edu.syr.hw1;

import java.util.ArrayList;
import java.util.List;

public class Library {
    // Catalog variable is made private in order to restrict the users from deleting the titles from catalog
    private List<Book> catalog;

    public void add (Book bookObj) {
        this.catalog.add(bookObj);
    }

    // No static keyword, as the catalog is an attibute of the Library object.
    // Every Library object needs to search in it's own catalog.
    public Book search (Book b) {
        // Loop through all titles in the catalog
        for (Book book : catalog) {
            // Exact match case
            if (book.matches(b)) {
                return book;
            }
        }
        return null;
    }
}