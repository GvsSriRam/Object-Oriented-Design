package edu.syr.hw1;

public class Library {
    private String[] catalog; // catalog variable is made private in order to restrict the users from deleting the titles from catalog

    public void init (String[] arr) { // no static keyword, as the initialization of catalog is different for different libraries / Library Objects.
        this.catalog = arr;
    }

    public String search (String str) { // no static keyword, as the catalog is an attibute of the Library object.
        if str == null {
            System.out.println("Search string is null");
            return null;
        }
        else if catalog == null {
            System.out.println("Library catalog is null");
            return null;
        }

        for (String title : catalog) {
            // Exact match case
            if (title.equalsIgnoreCase(str)) {
                return title;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String[] input = {"The Go Programming Language, Alan Donovan and Brian Kernighan", "New book"};
        Library libObj = new Library();
        libObj.init(input);

        String matchingTitle = libObj.search("New book");
        System.out.println(matchingTitle);
    }
}