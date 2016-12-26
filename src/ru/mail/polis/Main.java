package ru.mail.polis;

import java.util.Comparator;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        ISortedSet<Integer> set = new RedBlackTree<>();
        TreeSet<Integer> treeSet = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
            treeSet.add(i);
            assert set.size() == treeSet.size();
            assert set.first().equals(treeSet.first());
            assert set.last().equals(treeSet.last());
        }
        System.out.println();
        System.out.println(set.inorderTraverse());
        System.out.println(treeSet);
        System.out.println();

        for (int i = 0; i < 9; i++) {
            assert set.remove(i) == treeSet.remove(i);
            assert set.size() == treeSet.size();
            assert set.first().equals(treeSet.first());
        }

        Comparator<Integer> EVEN_FIRST = (v1, v2) -> {
            // Even first
            final int c = Integer.compare(v1 % 2, v2 % 2);
            return c != 0 ? c : Integer.compare(v1, v2);
        };

        RedBlackTree<Integer> Set = new RedBlackTree<>(EVEN_FIRST);
        Set.add(0);
        Set.add(1);
        Set.add(2);
        Set.print();

        System.out.println(Set.size());
        System.out.println(Set.first());
        System.out.println(Set.last());

    }

}