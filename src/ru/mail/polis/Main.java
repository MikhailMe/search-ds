package ru.mail.polis;

public class Main {

    public static void main(String[] args) {
        ISortedSet<Integer> set = new AVLTree<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);

            System.out.println("last =" + set.last());
            //System.out.println("\n\n\n\n\n");
        }




    }

}