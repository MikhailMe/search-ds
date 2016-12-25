package ru.mail.polis;

public class Main {

    public static void main(String[] args) {
        /*AVLTree<Integer> set = new AVLTree<>();
        System.out.println(set.add(5));
        System.out.println(set.add(6));
        System.out.println(set.add(7));
        System.out.println(set.add(8));

        System.out.println(set.remove(-5));
        System.out.println(set.remove(-6));
        System.out.println(set.remove(-8));

        System.out.println(set.add(10));
        System.out.println(set.remove(10));*/

        OpenHashTable<Integer> hashTable = new OpenHashTable<>();
        hashTable.add(5);
        hashTable.printHT();
    }
}