package ru.mail.polis;

public class Main {

    public static void main(String[] args) {
        RedBlackTree<Integer> set = new RedBlackTree<>();
        System.out.println(set.add(5));
        System.out.println(set.add(6));
        System.out.println(set.add(7));
        System.out.println(set.add(8));
        System.out.println();
        System.out.println(set.remove(5));
        System.out.println(set.remove(6));
        System.out.println(set.remove(8));
        System.out.println();
        System.out.println(set.add(10));
        System.out.println(set.remove(10));
    }
}