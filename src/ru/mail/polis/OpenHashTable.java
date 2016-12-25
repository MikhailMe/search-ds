package ru.mail.polis;

import java.util.ArrayList;
import java.util.Comparator;

//TODO: write code here
public class OpenHashTable<E extends Comparable<E>> implements ISet<E> {

    private E[] table;
    private Comparator<E> comparator;
    private static final int CAPACITY = 8;
    private static float loadFactor;
    private int size;

    public OpenHashTable() {
        table = getData();
        loadFactor = 0.5f;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private E[] getData(E... empty) {
        ArrayList<E> res = new ArrayList<>(CAPACITY);
        return res.toArray(empty);
    }

    public OpenHashTable(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void printHT(){
        for (E elem : table)
            System.out.println(elem);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E value) {
        return false;
    }

    @Override
    public boolean add(E value) {
        table[0] = value;
        return true;
    }

    @Override
    public boolean remove(E value) {
        return false;
    }
}