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

    // A book should have all of it's fields set properly in order to be considered for library catalog
    // Utility method
    // Made private since we won't need to use it outside of the library class
    // Made static since we don't need to instantiate the library to verify if the Book object is eligible to be considered for the catalog
    private static void validateBookForLibrary(Book book) {
        if (book.getAuthor() == null || Book.cleanString(book.getAuthor()).isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        if (book.getTitle() == null || Book.cleanString(book.getTitle()).isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (book.getPublisher() == null || Book.cleanString(book.getPublisher()).isEmpty()) {
            throw new IllegalArgumentException("Publisher cannot be null or empty");
        }
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        if (book.getYearPublished() == -1) {
            throw new IllegalArgumentException("Year Published cannot be -1 or empty");
        }
        if ((book.getType() == null || book.getType().equals(BookType.ANY))) {
            throw new IllegalArgumentException("Book Type cannot be null or ANY");
        }
    }

    public void add(Book b) {
        // Validate the book before adding it to the catalog
        try {
            validateBookForLibrary(b);
            // Validated, so, add into the catalog
            catalog.add(b);
        } catch (IllegalArgumentException e) { // Catch and display the exception
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Book> search(Book lookFor) {
        List<Book> results = new ArrayList<>();
        for (Book curr: catalog) {
            // clean string - trims, converts string to lower case, removed non-aplhabetical characters and normalize spaces
            // It also returns empty string for null input
            // Utility method defined in Book.java
            String authorCleaned = Book.cleanString(curr.getAuthor());
            String lookForAuthorCleaned = Book.cleanString(lookFor.getAuthor());
            if (lookForAuthorCleaned.length() != 0 && !authorCleaned.contains(lookForAuthorCleaned)) {
                continue;
            }

            String titleCleaned = Book.cleanString(curr.getTitle());
            String lookForTitleCleaned = Book.cleanString(lookFor.getTitle());
            if (lookForTitleCleaned.length() != 0 && !titleCleaned.contains(lookForTitleCleaned)) {
                continue;
            }

            String publisherCleaned = Book.cleanString(curr.getPublisher());
            String lookForPublisherCleaned = Book.cleanString(lookFor.getPublisher());
            if (lookForPublisherCleaned.length() != 0 && !publisherCleaned.contains(lookForPublisherCleaned)) {
                continue;
            }

            if (lookFor.getIsbn() != null && !lookFor.getIsbn().trim().isEmpty()) {
                if (!lookFor.getIsbn().equals(curr.getIsbn())) {
                    continue;
                }
            }

            if (lookFor.getYearPublished() != -1) {
                if (lookFor.getYearPublished() != curr.getYearPublished()) {
                    continue;
                }
            }

            if (!lookFor.getType().equals(BookType.ANY) && !curr.getType().equals(lookFor.getType())) {
                continue;
            }
            results.add(curr);
        }
        return results;
    }

//    public static void main(String[] args) {
//        Library lib = new Library();
//        lib.add(new Book("Holden Karau", "Learning Spark", "O'Reilly", "9781449358624", 2015, BookType.HARDCOVER));
//        Book b2 = new Book("Norman Matloff", "The Art of R Programming", "No Starch Press", "9781593273842", 2011, BookType.PAPERBACK);
//        Book b3 = new Book("Alan A. A. Donovan", "The Go Programming Language", "Addison Wesley", "9780134190440", 2016, BookType.EBOOK);
//        List<Book> results = lib.search(new Book(null, "Learning Spark", null, null, -1, BookType.ANY)); // Repositioned "Learning Spark" from publisher to title
//        for (Book b: results) {
//            System.out.println(b.toString());
//        }
//    }

    public static void main(String[] args) {
        // Create a Library instance and populate it with books
        Library lib = new Library();
        lib.add(new Book("Holden Karau", "Learning Spark", "O'Reilly", "9781449358624", 2015, BookType.HARDCOVER));
        lib.add(new Book("Holden Karau", "Learning Spark in 30 days", "Syracuse Publications", "9781449358646", 2016, BookType.EBOOK));
        lib.add(new Book("Norman Matloff", "The Art of R Programming", "No Starch Press", "9781593273842", 2011, BookType.PAPERBACK));
        lib.add(new Book("Alan A. A. Donovan", "The Go Programming Language", "Addison Wesley", "9780134190440", 2016, BookType.EBOOK));
        lib.add(new Book("Author Unknown", "Some Unknown Book", "Unknown Publisher", null, -1, BookType.ANY)); // null in ISBN
        lib.add(new Book(" ", "The Go Programming Language", "Addison Wesley", "9780134190440", 2016, BookType.EBOOK)); // Just space in author name
        lib.add(new Book("Alan A. A. Donovan", "&%", "Addison Wesley", "9780134190440", 2016, BookType.EBOOK)); // Just spl characters in title

        // 1. Test case: Search by full author name
        System.out.println("\nTest Case 1: Search by author name 'Holden Karau'");
        List<Book> result = lib.search(new Book("Holden Karau", null, null, null, -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        // 2. Test case: Search by partial title
        System.out.println("\nTest Case 2: Search by partial title 'Learning'");
        result = lib.search(new Book(null, "Learning", null, null, -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        // 3. Test case: Search by publisher and title
        System.out.println("\nTest Case 3: Search by publisher 'O'Reilly' and title 'Learning Spark'");
        result = lib.search(new Book(null, "Learning Spark", "O'Reilly", null, -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        // 4. Test case: Search by ISBN only
        System.out.println("\nTest Case 4: Search by ISBN '9781449358624'");
        result = lib.search(new Book(null, null, null, "9781449358624", -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        // 5. Test case: Search with yearPublished and author
        System.out.println("\nTest Case 5: Search by author 'Norman Matloff' and yearPublished 2011");
        result = lib.search(new Book("Norman Matloff", null, null, null, 2011, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        // 6. Test case: Search with all fields set
        System.out.println("\nTest Case 6: Search by all fields (Learning Spark, Holden Karau, O'Reilly, 2015, HARDCOVER)");
        result = lib.search(new Book("Holden Karau", "Learning Spark", "O'Reilly", "9781449358624", 2015, BookType.HARDCOVER));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        // 7. Test case: Search with null author and empty title
        System.out.println("\nTest Case 7: Search with null author and empty title");
        result = lib.search(new Book(null, "", null, null, -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        // 8. Test case: Search with missing yearPublished (-1)
        System.out.println("\nTest Case 8: Search with missing yearPublished");
        result = lib.search(new Book("Alan A. A. Donovan", null, null, null, -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        // 9. Test case: Search by type only (BookType.EBOOK)
        System.out.println("\nTest Case 9: Search by BookType 'EBOOK'");
        result = lib.search(new Book(null, null, null, null, -1, BookType.EBOOK));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        // 10. Test case: Search for non-existing book
        System.out.println("\nTest Case 10: Search for non-existing book (title: 'Non Existing')");
        result = lib.search(new Book(null, "Non Existing", null, null, -1, BookType.ANY));
        if (result.isEmpty()) {
            System.out.println("No results found.");
        } else {
            for (Book b : result) {
                System.out.println(b.toString());
            }
        }

        // 11. Test case: Search by only nulls (empty search)
        System.out.println("\nTest Case 11: Search by all nulls (should return all books)");
        result = lib.search(new Book(null, null, null, null, -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        //12. Test case: Search for books by Holden (partial of Holden Karau) released in year 2015
        System.out.println("\nTest Case 12: Search for books by Holden in 2015, wil null as type");
        // null BookType should be considered as ANY
        result = lib.search(new Book("holden", null, null, null, 2015, null));
        for (Book b: result) {
            System.out.println(b.toString());
        }

        //13. Test case: Search for books by Holden (partial of Holden Karau) without specifying year
        System.out.println("\nTest Case 13: Search by case-insensitive author 'holden karau'");
        result = lib.search(new Book("holden karau", null, null, null, -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        System.out.println("\nTest Case 14: Search with leading/trailing spaces in title ' Learning Spark '");
        result = lib.search(new Book(null, " Learning Spark ", null, null, -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        System.out.println("\nTest Case 15: Search by title with special characters 'O'Reilly%");
        result = lib.search(new Book(null, null, "O'Reilly%", null, -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        System.out.println("\nTest Case 16: Search by partial ISBN '978'");
        result = lib.search(new Book(null, null, null, "978", -1, BookType.ANY));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        System.out.println("\nTest Case 17: Search with invalid year (-999) and invalid BookType (BookType.PAPERBACK)");
        result = lib.search(new Book(null, null, null, null, -999, BookType.PAPERBACK));
        for (Book b : result) {
            System.out.println(b.toString());
        }

        System.out.println("\nTest Case 18: Search for a book with a future publication year (e.g., 2100)");
        result = lib.search(new Book(null, null, null, null, 2100, BookType.ANY));
        if (result.isEmpty()) {
            System.out.println("No results found.");
        } else {
            for (Book b : result) {
                System.out.println(b.toString());
            }
        }

        System.out.println("\nTest Case 19: Search for a book with conflicting author and title");
        result = lib.search(new Book("Alan A. A. Donovan", "Learning Spark", null, null, -1, BookType.ANY));
        if (result.isEmpty()) {
            System.out.println("No results found.");
        } else {
            for (Book b : result) {
                System.out.println(b.toString());
            }
        }

    }
}

