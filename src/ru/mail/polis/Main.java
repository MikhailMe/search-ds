package ru.mail.polis;

public class Main {

    public static void main(String[] args) {
        ISortedSet<Integer> set = new RedBlackTree<>();
        for (int i = 0; i < 10; i++)
            set.add(i);
        System.out.println(set.inorderTraverse());
    }
}