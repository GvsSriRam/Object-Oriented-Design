package edu.syr.hw1;

public class Greeting {
    public void greet() { // Since there is no static keyword, the greet method can be called only by creating an instance.
        System.out.println("Hello World");
    }
    // No need of main method as we are calling this in another Runner.
}