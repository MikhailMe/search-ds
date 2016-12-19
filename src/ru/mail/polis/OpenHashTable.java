package ru.mail.polis;

import java.util.Comparator;

//TODO: write code here
public class OpenHashTable<E extends Comparable<E>> implements ISet<E> {

    private E[] table;
    private Comparator<E> comparator;
    private static final int CAPACITY = 8;
    private static float loadFactor;
    private Object[] objectTable;
    private int size;

    public OpenHashTable() {
        objectTable = new Object[CAPACITY];
        loadFactor = 0.5f;
        size = 0;
    }

    public E getElement(int hash){
        return (E) objectTable[hash];
    }

    public OpenHashTable(Comparator<E> comparator) {
        this.comparator = comparator;
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
        if (isEmpty()){

        }
        return true;
    }

    @Override
    public boolean remove(E value) {
        return false;
    }
}