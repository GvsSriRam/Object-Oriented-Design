package edu.syr.hw3;

import edu.syr.hw3.BookType;

public class Book {
    private String author;
    private String title;
    private String publisher;
    private String isbn;
    private int yearPublished;
    private BookType type;

    public Book(String author, String title, String publisher, String isbn, int yearPublished, BookType bookType) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.isbn = isbn;
        this.yearPublished = yearPublished==0 ? -1: yearPublished; // Year -1 is considered as default value which should match any year in search time
        this.type = bookType==null ? BookType.ANY : bookType; // BookType ANY should match any book during search, but not be assigned to a catalog
    }

    public String getAuthor() {
        return this.author;
    }
    public String getTitle() {
        return this.title;
    }
    public String getPublisher() {
        return this.publisher;
    }
    public String getIsbn() {
        return this.isbn;
    }
    public int getYearPublished() {
        return this.yearPublished;
    }
    public BookType getType() {
        return this.type;
    }

    public boolean matches(Book b) {
        if (!author.equals("") && !author.toLowerCase().contains(b.author.toLowerCase())) {
            return false;
        }
        if (!title.equals("") && !title.toLowerCase().contains(b.title.toLowerCase())) {
            return false;
        }
        if (!publisher.equals("") && !publisher.toLowerCase().contains(b.publisher.toLowerCase())) {
            return false;
        }
        if (!isbn.equals("") && ! b.isbn.equals("") && !isbn.equals(b.isbn)) {
            return false;
        }
        if (yearPublished > 1700 && b.yearPublished> 1700 && yearPublished != b.yearPublished) {
            return false;
        }
        if (!type.equals(BookType.ANY) && !type.equals(b.type)) {
            return false;
        }
        return true;
    }

    // Utility method to clean string before considering for search
    // Made public because we might need it for Library as well
    // Made static as we don't depend on any instance / private attributes
    public static String cleanString(String input) {
        // handle null case
        if (input == null) return "";

         // Trip trailing and leading spaces
         input = input.trim();
         // lowercase, remove non-alphabet characters, and normalize spaces
         return input.toLowerCase().replaceAll("[^a-zA-Z]", "").replaceAll("\\s+", " ");
     }

    @Override
    public String toString() {
        // Format the output as "title by author published by publisher in yearPublished with ISBN isbn"
        // Using string builder to build the string
        // Handles null / unknown cases for each field
        StringBuilder sb = new StringBuilder();
        sb.append(title != null ? title: "Unknown Title");
        sb.append(" by ").append(author != null ? author: "Unknown Author");
        sb.append(" published by ").append(publisher != null ? publisher: "Unknown Publisher");
        sb.append(" in ").append(yearPublished != -1 ? yearPublished : "Unknown Year");
        sb.append(" with ISBN ").append(isbn != null ? isbn: "Unknown ISBN");
        sb.append(" type - ").append(!type.equals(BookType.ANY) ? type.toString(): "Unknown Type");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof Book)) {
            return false;
        }
        Book that = (Book) o;
        // Handles null value cases in equals
        // If the field is null in one object and not null in the other, then return false
        // If the field is null in both objects, then it is okay, continue eqality check for the next field
        // If the field is not null in both objects, then compare the field values
        // For each field, check if both are null, or compare values if both are not null
        if ((this.author == null && that.getAuthor() != null) || (this.author != null && !this.author.equals(that.getAuthor()))) {
            return false;
        }
        if ((this.title == null && that.getTitle() != null) || (this.title != null && !this.title.equals(that.getTitle()))) {
            return false;
        }
        if ((this.publisher == null && that.getPublisher() != null) || (this.publisher != null && !this.publisher.equals(that.getPublisher()))) {
            return false;
        }
        if ((this.isbn == null && that.getIsbn() != null) || (this.isbn != null && !this.isbn.equals(that.getIsbn()))) {
            return false;
        }
        if (this.yearPublished != that.getYearPublished()) {
            return false;
        }
        if ((this.type == null && that.getType() != null) || (this.type != null && !this.type.equals(that.getType()))) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17; // Initialize to a prime number

        // Multiply by a prime number and add the hash code of each field
        // Handles null values hascodes in the fields
        result = 31 * result + (author == null ? 0 : author.hashCode());
        result = 31 * result + (title == null ? 0 : title.hashCode());
        result = 31 * result + (publisher == null ? 0 : publisher.hashCode());
        result = 31 * result + (isbn == null ? 0 : isbn.hashCode());
        result = 31 * result + yearPublished;
        result = 31 * result + (type == null ? 0 : type.hashCode());

        return result;
    }


    public static void main(String[] args) {
        Book b1 = new Book("Holden Karau", "Learning Spark", "O'Reilly", "9781449358624", 2015, BookType.HARDCOVER);
        Book b2 = new Book("Norman Matloff", "The Art of R Programming", "No Starch Press", "9781593273842", 2011, BookType.PAPERBACK);
        Book b3 = new Book("Alan A. A. Donovan", "The Go Programming Language", "Addison Wesley", "9780134190440", 2016, BookType.EBOOK);
        Book b4 = new Book("Holden Karau", "Learning Spark", "O'Reilly", "9781449358624", 2015, BookType.HARDCOVER);

        System.out.println("\nEquals tests\n");
        System.out.println(b1.equals(b1)); // true
        System.out.println(b1.equals(b2)); // false
        System.out.println(b1.equals(b3)); // false
        System.out.println(b1.equals(b4)); // true

        Book b5 = new Book(null, "Learning Spark", "O'Reilly", "9781449358624", 2015, BookType.HARDCOVER);
        System.out.println(b1.equals(b5));  // Expected: false

        Book b6 = new Book(null, "Learning Spark", null, "9781449358624", 2015, BookType.HARDCOVER);
        Book b7 = new Book(null, "Learning Spark", null, "9781449358624", 2015, BookType.HARDCOVER);
        System.out.println(b6.equals(b7));  // Expected: true

        Book b8 = new Book("Holden Karau", "Learning Spark", "O'Reilly", "9781449358624", 2016, BookType.HARDCOVER);
        System.out.println(b1.equals(b8));  // Expected: false

        System.out.println("\nHashcode tests\n");
        System.out.println(b1.hashCode() == b1.hashCode()); // true
        System.out.println(b1.hashCode() == b2.hashCode()); // false
        System.out.println(b1.hashCode() == b3.hashCode()); // false
        System.out.println(b1.hashCode() == b4.hashCode()); // true

        Book b9 = new Book("Learning Spark", "Holden Karau", "O'Reilly", "9781449358624", 2015, BookType.HARDCOVER);

        System.out.println(b1.hashCode());
        System.out.println(b9.hashCode());

        Book b10 = new Book(null, "Learning Spark", null, null, 0, null);
        Book b11 = new Book("Learning Spark", null, null, null, 0, null);

        System.out.println(b10.hashCode());
        System.out.println(b11.hashCode());

        System.out.println("\ntoString tests\n");
        System.out.println(b1.toString()); // Learning Spark by Holden Karau published by O'Reilly in 2015 with ISBN 9781449358624
        System.out.println(b2.toString()); // The Art of R Programming by Norman Matloff published by No Starch Press in 2011 with ISBN 9781593273842
        System.out.println(b3.toString()); // The Go Programming Language by Alan A. A. Donovan published by Addison Wesley in 2016 with ISBN 9780134190440
        System.out.println(b4.toString()); // Learning Spark by Holden Karau published by O'Reilly in 2015 with ISBN 9781449358624
        System.out.println(b5.toString());
        System.out.println(b6.toString());
    }
}

