package ru.mail.polis;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        /*AVLTree<Integer> set = new AVLTree<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
            set.print();

            System.out.println("last =" + set.last());
            System.out.println("\n\n\n\n\n");
        }*/


        Random r = new Random();
        ISortedSet<Integer> set = new RedBlackTree<Integer>();
        for (int i = 0; i < 1000; i++) {
            set.add(r.nextInt(1000));
        }
        for (int i = 0; i < 1000; i++) {
            set.remove(r.nextInt(1000));
        }

    }

}