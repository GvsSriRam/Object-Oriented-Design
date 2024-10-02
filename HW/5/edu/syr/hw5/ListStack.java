package edu.syr.hw5;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class ListStack<E> implements Stack<E> {
    private List<E> list;

    public ListStack() {
        list = new ArrayList<>();
    }

    @Override
    public void push(E e) {
        list.add(e);
    }

    @Override
    public E pop() {
        if (!list.isEmpty()) {
            return list.remove(list.size() - 1);
        }
        throw new EmptyStackException("Stack is empty");
    }

    @Override
    public E peek() {
        if (!list.isEmpty()) {
            return list.get(list.size() - 1);
        }
        throw new EmptyStackException("Stack is empty");
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<E> getData() {
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    public static void main(String[] args) {
        ListStack<Integer> stack = new ListStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
//        stack.push("4");
        System.out.println("Size: " + stack.size()); // Output: 3
        System.out.println("Peek: " + stack.peek()); // Output: 3
        System.out.println("Pop: " + stack.pop()); // Output: 3
        System.out.println("Size: " + stack.size()); // Output: 2
        System.out.println("Data: " + stack.getData()); // Output: [1, 2] (unmodifiable)

        //Attempting to modify the returned list will throw exception
        List<Integer> data = stack.getData();
        try {
            data.add(4); //UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("Unmodifiable stack");
            System.out.println("Exception caught: " + e.getMessage());
        }

        stack.pop();
        stack.pop();
        try {
            System.out.println(stack.pop()); //Throws EmptyStackException after popping all elements
        } catch (EmptyStackException e) {
            System.out.println("Empty stack");
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}