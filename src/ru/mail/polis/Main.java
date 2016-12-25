package ru.mail.polis;

import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {

        TreeSet<Integer> OK = new TreeSet<>();
        ISortedSet<Integer> set = new AVLTree<>();
        //ISortedSet<Integer> set = new RedBlackTree<>();
        for (int value = 0; value < 10; value++) {
            assert OK.add(value) == set.add(value);
        }
        assert OK.last().equals(set.last());
        for (int value = 10; value >= 0; value--) {
            assert OK.remove(value) == set.remove(value);
            assert OK.remove(value) == set.remove(value);
            if (!OK.isEmpty()) {
                assert OK.first().equals(set.first());
            }
        }
    }
}