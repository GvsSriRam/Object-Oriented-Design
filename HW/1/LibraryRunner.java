public class LibraryRunner {
    public static void main(String[] args) {

        String[] input = {"The Go Programming Language, Alan Donovan and Brian Kernighan", "New book"};
        Library libObj = new Library();
        libObj.init(input);

        String matchingTitle = libObj.search("Go");
        System.out.println(matchingTitle);
    }
}