package ru.mail.polis;

import java.util.*;

public class AVLTree<T extends Comparable<T>> implements ISortedSet<T>{

    private final Comparator<T> comparator;
    private Node root;
    private int size;
    private boolean flag = true;

    public AVLTree() {
        root = null;
        size = 0;
        this.comparator = null;
    }

    public AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private int maxDepth(Node current) {
        if (current == null)
            return -1;
        return 1 + Math.max(maxDepth(current.left), maxDepth(current.left));
    }

    private int minDepth(Node current) {
        if (current == null)
            return -1;
        return 1 + Math.min(minDepth(current.left), minDepth(current.right));
    }

    private boolean isBalanced(Node current) {
        return (maxDepth(current) - minDepth(current) <= 1);
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private int height(Node x, Node y) {
        if (x == null && y == null) return 0;
        else if (x == null) return y.h;
        else if (y == null) return x.h;
        else return Math.max(x.h, y.h);
    }

    private int balance(Node x, Node y) {
        if (x == null && y == null) return 0;
        else if (x == null) return -y.h;
        else if (y == null) return x.h;
        else return x.h - y.h;
    }

    private Node min(Node current) {
        if (current.left == null) return current;
        return min(current.left);
    }

    public T min() {
        return root == null ? null : min(root).data;
    }

    private Node max(Node current) {
        if (current.right == null) return current;
        return max(current.right);
    }

    public T max() {
        return root == null ? null : max(root).data;
    }

    private Node insert(Node current, T data, Node parent) {
        if (data == null) {
            throw new NullPointerException("null");
        }
        if (current == null) {
            size++;
            flag = true;
            return new Node(data, parent);
        }
        int compareResult = compare(data,current.data);
        if (compareResult > 0) {
            current.right = insert(current.right, data, current);
            current.h = height(current.left, current.right) + 1;
        } else if (compareResult < 0) {
            current.left = insert(current.left, data, current);
            current.h = height(current.left, current.right) + 1;
        } else {
            current.data = data;
            flag = false;
        }
        current.balance = balance(current.left, current.right);
        if (current.balance == -2)
            current = leftRotation(current);
        else if (current.balance == 2)
            current = rightRotation(current);
        return current;
    }

    public void insert(T data) {
        root = insert(root, data, null);
    }

    private Node delete(Node current, T data) {
        if (data == null) {
            throw new NullPointerException("null");
        }
        if (current == null) return null;
        int compareResult = compare(data,current.data);
        if (compareResult > 0)
            current.right = delete(current.right, data);
        else if (compareResult < 0)
            current.left = delete(current.left, data);
        else {
            if (current.right == null && current.left == null)
                current = null;
            else if (current.right == null) {
                current.left.parent = current.parent;
                current = current.left;
            } else if (current.left == null) {
                current.right.parent = current.parent;
                current = current.right;
            } else {
                if (current.right.left == null) {
                    current.right.left = current.left;
                    current.right.parent = current.parent;
                    current.right.parent = current.parent;
                    current.left.parent = current.right;
                    current = current.right;
                } else {
                    Node result = min(current.right);
                    current.data = result.data;
                    delete(current.right, current.data);
                }
            }
        }
        if (current != null) {
            current.h = height(current.left, current.right) + 1;
            current.h = balance(current.left, current.right);
            if (current.balance == -2)
                current = leftRotation(current);
            else if (current.balance == 2)
                current = rightRotation(current);
        }
        return current;
    }

    private void delete(T data) {
        root = delete(root, data);
    }

    private Node leftRotation(Node current) {
        if (current.right.right == null && current.right.left != null) {
            current.right = rightRotation(current.right);
            current = leftRotation(current);
        } else if (current.right.left == null ||
                current.right.left.h <= current.right.right.h) {
            Node node = current.right;
            node.parent = current.parent;
            current.right = node.left;
            if (current.right != null)
                current.right.parent = current;
            current.h = height(current.left, current.right) + 1;
            current.parent = node;
            current.balance = balance(current.left, current.right);
            node.left = current;
            current = node;
            current.balance = balance(current.left, current.right);
            current.h = height(current.left, current.right) + 1;
        } else {
            current.right = rightRotation(current.right);
            current = leftRotation(current);
        }
        return current;
    }

    private Node rightRotation(Node current) {
        if (current.left.right != null &&
                current.left.left == null) {
            current.left = leftRotation(current.left);
            current = rightRotation(current);
        } else if (current.left.right == null ||
                current.left.right.h <= current.left.left.h) {
            Node node = current.left;
            node.parent = current.parent;
            current.left = node.right;
            if (current.left != null)
                current.left.parent = current;
            current.h = height(current.left, current.right) + 1;
            current.parent = node;
            current.balance = balance(current.left, current.right);
            node.right = current;
            current = node;
            current.balance = balance(current.left, current.right);
            current.h = height(current.left, current.right) + 1;
        } else {
            current.left = leftRotation(current.left);
            current = rightRotation(current);
        }
        return current;
    }

    @Override
    public T first() {
        if (isEmpty()) throw new NoSuchElementException("Set is empty");
        Node curr = root;
        while (curr.left != null)
            curr = curr.left;
        return curr.data;
    }

    @Override
    public T last() {
        if (isEmpty()) throw new NoSuchElementException("Set is empty");
        if (root.right.data != null)
            if (compare(root.data,root.right.data) < 0)
                return root.data;
        Node curr = root;
        while (curr.right != null)
            curr = curr.right;
        return curr.data;
    }

    @Override
    public List<T> inorderTraverse() {
        List<T> list = new ArrayList<T>();
        return inOrder(root, list);
    }

    private List<T> inOrder(Node node, List<T> list) {
        if (node != null) {
            inOrder(node.left, list);
            list.add(node.data);
            inOrder(node.right, list);
        }
        return list;
    }

    private T find(Node current, T data) {
        if (current == null) return null;
        int compareResult = compare(data,current.data);
        if (compareResult == 0)
            return current.data;
        else if (compareResult > 0)
            return find(current.right, data);
        else return find(current.left, data);
    }

    private T find(T data) {
        return find(root, data);
    }

    private void print(Node current, int level) {
        if (current != null) {
            print(current.right, level + 1);
            for (int i = 0; i < level; ++i)
                System.out.print("\t");
            System.out.println(current.data);
            print(current.left, level + 1);
        }
    }

    public void print() {
        print(root, 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

/*    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
        return contains((T) o);
    }*/

    @Override
    public boolean contains(T value) {
        return find((T) value) != null;
    }

/*
    @Override
    public Iterator iterator() {
        return new Iterator();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = 0;
        for (T it : this) {
            result[index] = it;
            index++;
        }
        return result;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length >= size) {
            int index = 0;
            for (T i : this) {
                a[index] = (T1) i;
                index++;
            }
            return a;
        } else {
            T1[] b = (T1[]) new Object[size];
            int index = 0;
            for (T i : this) {
                b[index] = (T1) i;
                index++;
            }
            return b;
        }
    }
*/

    @Override
    public boolean add(T value) throws  NullPointerException{
        if (value == null) {
            throw new NullPointerException("null");
        }
        root = insert(root, value, null);
        return flag;
    }

/*
    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object value) {
        if (value == null) {
            throw new NullPointerException("null");
        }
        root = delete(root, (T) value);
        if (flag) size--;
        return flag;
    }
*/

    @Override
    public boolean remove(T value) {
        if (value == null) {
            throw new NullPointerException("null");
        }
        boolean result = helpRemove(root, value);
        if (result) size--;
        return result;
    }

    private boolean helpRemove(Node node, T value){
        boolean result;
        if(node == null) {
            result = false;
        } else {
            if(compare(node.data, value) > 0)  {
                result = helpRemove(node.left, value);
            } else if(compare(node.data, value) < 0) {
                result = helpRemove(node.right, value);
            } else {
                delete(node.data);
                result = true;
            }
        }
        return result;
    }

/*    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof AVLTree) {
            AVLTree<T> tree = (AVLTree<T>) o;
            return (size == tree.size && containsAll(tree));
        }
        return false;
    }*/

/*    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object it : c)
            if (!contains(it))
                return false;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(Collection<? extends T> c) {
        int counter = this.size;
        for (Object it : c)
            add((T) it);
        if (this.size > counter)
            return true;
        else return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeAll(Collection<?> c) {
        int counter = this.size;
        for (Object it : c)
            if (contains(it))
                remove((T) it);
        if (this.size < counter)
            return true;
        else return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(Collection<?> c) {
        int counter = this.size;
        LinkedList list = new LinkedList();
        for (T it : this)
            if (!c.contains(it))
                list.add(it);
        removeAll(list);
        return this.size < counter;
    }

    @Override
    public void clear() {
        this.forEach(this::remove);
    }*/

    @Override
    public int hashCode() {
        int hashRoot = root.hashCode();
        boolean booleanValue = true;
        char charValue = 'd';
        String stringValue = "someTestForHashCode";
        long longValue = 34829245849498300l;
        float floatValue = 345832400.93f;
        double doubleValue = 98584292348454.9834;
        byte[] arrayValue = {1, 2, 3};
        int result = 26 * hashRoot;
        result = 26 * result + (booleanValue ? 1 : 0);
        result = 26 * result + (int) charValue;
        result = 26 * result + stringValue.hashCode();
        result = 26 * result + (int) (longValue - (longValue >>> 32));
        result = 26 * result + Float.floatToIntBits(floatValue);
        long longBits = Double.doubleToLongBits(doubleValue);
        result = 26 * result + (int) (longBits - (longBits >>> 32));
        for (byte b : arrayValue)
            result = 26 * result + (int) b;
        return result;
    }

    private int compare(T v1, T v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }

    private class Node {

        private T data;
        private int h;
        private int balance;
        private Node left;
        private Node right;
        private Node parent;

        public Node() {
            this.data = null;
            left = this;
            right = this;
            parent = this;
        }

        public Node(T newData, Node newParent) {
            this.data = newData;
            this.parent = newParent;
            this.left = null;
            this.right = null;
            this.h = 1;
            this.balance = 0;
        }

        public int hashcode() {
            int result = 26;
            result *= this.h;
            result *= this.balance;
            result *= data.hashCode();
            return result;
        }
    }

    public class Iterator implements java.util.Iterator<T> {

        private Node it = null;
        private Stack<Node> stack = new Stack<>();

        public Iterator() {
            it = root;
            if (it == null) return;
            stack.push(null);
            while (it.left != null) {
                stack.push(it);
                it = it.left;
            }
        }

        @Override
        public boolean hasNext() {
            return it != null;
        }

        @Override
        public T next() {
            T val;
            if (it != null)
                val = it.data;
            else throw new NoSuchElementException();
            if (it.right != null) {
                it = it.right;
                while (it.left != null) {
                    stack.push(it);
                    it = it.left;
                }
            } else it = stack.pop();
            return val;
        }

        @Override
        public void remove() {
        }
    }
}