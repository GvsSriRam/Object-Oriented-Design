package edu.syr.hw1;

import java.util.ArrayList;
import java.util.List;

public class Library {
    // Catalog variable is made private in order to restrict the users from deleting the titles from catalog
    private List<String> catalog;

    // No static keyword, as the initialization of catalog is different for different libraries / Library Objects.
    public void init (String[] arr) {
        this.catalog = new ArrayList<>();
        for (String title : arr) {
            this.catalog.add(title);
        }
    }

    // Method to clean search string
    // Made private since we don't want our search string processing logic to be exposed outside this class
    // Can be initialized as a static method or a non-static method as it doesn't depend on catalog variable.
    // It is just a utility method.
    // I used static here, just to enable the usage of this method without creation of an instance.
    private static String cleanString(String input) {
        if (input == null) return "";

        // Trip trailing and leading spaces
        input = input.trim();
        // lowercase, remove non-alphabet characters, and normalize spaces
        return input.toLowerCase().replaceAll("[^a-zA-Z]", "").replaceAll("\\s+", " ");
    }

    // Method to check input's validity
    // Made private as it doesn't need to be exposed
    // No static keyword, since catalog is unique for each library object.
    private boolean isInputInvalid(String str) {
        // Check if the search string and catalog are null or empty
        if (str == null || str.length() == 0) {
            System.out.println("Search string is null");
            return true;
        } else if (catalog == null || catalog.size() == 0) {
            System.out.println("Library catalog is null");
            return true;
        }
        return false;
    }

    // No static keyword, as the catalog is an attibute of the Library object.
    // Every Library object needs to search in it's own catalog.
    public String search (String str) {
        // Check if the input search string or the library catalog is invalid
        if (isInputInvalid(str)) {
            return null;
        }

        // Variable to store the first partial match found
        String partialMatchResult = null;

        // Loop through all titles in the catalog
        for (String title : catalog) {
            // Exact match case
            if (title.equalsIgnoreCase(str)) {
                return title;
            }

            // Clean both the title and search string by removing special characters and spaces
            String titleCleaned = cleanString(title);
            String strCleaned = cleanString(str);

            // partial match - store only the first one
            if (titleCleaned.contains(strCleaned) && partialMatchResult == null) {
                partialMatchResult = title;
            }
        }
        return partialMatchResult;
    }

    public static void main(String[] args) {
        String[] input = {"The Go Programming Language, Alan Donovan and Brian Kernighan", "New book", "New book 2"};
        Library libObj = new Library();
        libObj.init(input);

        String matchingTitle = libObj.search("New");
        System.out.println(matchingTitle);

        matchingTitle = libObj.search("New book 2");
        System.out.println(matchingTitle);
    }
}