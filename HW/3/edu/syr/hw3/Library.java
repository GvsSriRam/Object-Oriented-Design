package edu.syr.hw3;

import java.util.ArrayList;
import java.util.List;
import edu.syr.hw3.Book;
import edu.syr.hw3.BookType;

public class Library {
    private List<Book> catalog;
    public Library() {
        catalog = new ArrayList<>();
    }
    public void add(Book b) {
        catalog.add(b);
    }
    public List<Book> search(Book lookFor) {
        System.out.println("Searching for: " + lookFor.toString());
        List<Book> results = new ArrayList<>();
        for (Book curr: catalog) {
            if (lookFor.getAuthor() != null) {
                System.out.println("Search for Author");
                if (!lookFor.getAuthor().equals(curr.getAuthor())) {
                    System.out.println("Author does not match");
                    continue;
                }
            }
            if (lookFor.getTitle() != null) {
                System.out.println("Search for Title");
                if (!lookFor.getTitle().equals(curr.getTitle())) {
                    System.out.println("Title does not match");
                    continue;
                }
            }
            if (lookFor.getPublisher() != null) {
                System.out.println("Search for Publisher");
                if (!lookFor.getPublisher().equals(curr.getPublisher())) {
                    System.out.println("Publisher does not match");
                    continue;
                }
            }
            if (lookFor.getIsbn() != null) {
                System.out.println("Search for ISBN");
                if (!lookFor.getIsbn().equals(curr.getIsbn())) {
                    System.out.println("Isbn does not match");
                    continue;
                }
            }
            if (lookFor.getYearPublished() != -1) {
                System.out.println("Search for Year Published");
                if (lookFor.getYearPublished() != curr.getYearPublished()) {
                    System.out.println("Year Published does not match");
                    continue;
                }
            }
            if (lookFor.getType() != BookType.ANY) {
                System.out.println("Search for Type");
                if (lookFor.getType() != curr.getType()) {
                    System.out.println("Type does not match");
                    continue;
                }
            }
            results.add(curr);
        }
        return results;
    }

    public List<Book> searchNew(Book lookFor) {
        List<Book> result = new ArrayList<>();
        for (Book curr: this.catalog) {
            if (curr.containsBook(lookFor)) {
                result.add(curr);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Library lib = new Library();
        lib.add(new Book("Holden Karau", "Learning Spark", "O'Reilly", "9781449358624", 2015, BookType.HARDCOVER));
        Book b2 = new Book("Norman Matloff", "The Art of R Programming", "No Starch Press", "9781593273842", 2011, BookType.PAPERBACK);
        Book b3 = new Book("Alan A. A. Donovan", "The Go Programming Language", "Addison Wesley", "9780134190440", 2016, BookType.EBOOK);
        List<Book> results = lib.searchNew(new Book(null, "Learning Spark", null, null, -1, BookType.ANY));
        for (Book b: results) {
            System.out.println(b.toString());
        }
    }
}

