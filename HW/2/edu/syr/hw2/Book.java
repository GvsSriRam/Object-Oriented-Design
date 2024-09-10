package edu.syr.hw2;

public class Book {
    private String author;
    private String title;
    private String publisher;
    private String isbn;
    private int yearPublished;

    public Book(String a, String t, String p, String i, int y) {
        this.author = a==null ? "" : a;
        this.title = t==null ? "" : t;
        this.publisher = p==null ? "" : p;
        this.isbn = i==null ? "" : i;
        this.yearPublished = y;
    }

    public String getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getIsbn() {
        return isbn;
    }
    public int getYearPublished() {
        return yearPublished;
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
        return true;
    }

    public String toString() {
        // Format the output as "title by author published by publisher in yearPublished with ISBN isbn"
        return title + " by " + author + " published by " + publisher + " in " + yearPublished + " with ISBN " + isbn;
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
        return author.equals(that.author) && title.equals(that.title) && publisher.equals(that.publisher) && isbn.equals(that.isbn) && yearPublished == that.yearPublished;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + author.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + publisher.hashCode();
        result = 31 * result + isbn.hashCode();
        result = 31 * result + yearPublished;
        return result;
    }


    public static void main(String[] args) {
        Book b1 = new Book("Holden Karau", "Learning Spark", "O'Reilly", "9781449358624", 2015);
        Book b2 = new Book("Norman Matloff", "The Art of R Programming", "No Starch Press", "9781593273842", 2011);
        Book b3 = new Book("Alan A. A. Donovan", "The Go Programming Language", "Addison Wesley", "9780134190440", 2016);
    }
}

