package edu.syr.hw3;

public class Node<T> {
    private Object value;
    public Node<T> next;

    public Node (T i) {
        this.value = i;
    }

    public static <T> Node<T> build(T[] data) {
        Node<T> list = new Node<T>(data[0]);
        Node<T> iter = list;

        for (int i = 1; i < data.length; i++) {
            iter.next = new Node<T>(data[i]);
            iter = iter.next;
        }
        return list;
    }

    public static void main(String[] args) {
        Node<String> s1 = new Node("hello");
        Node<Integer> i1 = new Node(Integer.valueOf(99));
        //s1.next = i1; //this should be a compile error

        String[] data = new String[]{"one", "two", "three"};
        Node<String> head = Node.build(data);
        while (head != null) {
            System.out.println(head.value);
            head = head.next;
        }
        StringBuffer[] sbData = new StringBuffer[]{new StringBuffer("uno"), new StringBuffer("dos"), new StringBuffer("tres")};
        Node<StringBuffer> h2 = Node.build(sbData);
        while (h2 != null) {
            System.out.println(h2.value.toString());
            h2 = h2.next;
        }
    }
}
