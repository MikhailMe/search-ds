package ru.mail.polis;

import java.util.*;

//TODO: write code here
public class RedBlackTree<T extends Comparable<T>> implements ISortedSet<T> {

    private final Comparator<T> comparator;
    private Node<T> nil = new Node<>();
    private Node<T> root = nil;
    private int size = 0;
    public RedBlackTree() {
        this.comparator = null;
    }
    public RedBlackTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private void turnLeft(Node<T> X) {
        Node<T> Y = X.right;
        X.right = Y.left;
        if (Y.left != nil) Y.left.parent = X;
        Y.parent = X.parent;
        if (X.parent == nil) root = Y;
        else if (X == X.parent.left) X.parent.left = Y;
        else X.parent.right = Y;
        Y.left = X;
        X.parent = Y;
    }

    private void turnRight(Node<T> Y) {
        Node<T> X = Y.left;
        Y.left = X.right;
        if (X.right != nil) X.right.parent = Y;
        X.parent = Y.parent;
        if (Y.parent == nil) root = X;
        else if (Y == Y.parent.right) Y.parent.right = X;
        else Y.parent.left = X;
        X.right = Y;
        Y.parent = X;
    }

    private void print(Node<T> current, int level) {
        if (current != null) {
            print(current.right, level + 1);
            for (int i = 0; i < level; ++i)
                System.out.print("\t");
            System.out.println(current.value);
            print(current.left, level + 1);
        }
    }

    public void print() {
        print(root, 0);
    }

    private Node<T> min(Node<T> current) {
        if (current.left == null) return current;
        return min(current.left);
    }

    public T min() {
        if (isEmpty()) throw new NoSuchElementException("Set is empty");
        return min(root).value;
    }

    private Node<T> max(Node<T> current) {
        if (current.right == null) return current;
        return max(current.right);
    }

    public T max() {
        return max(root).value;
    }

    @Override
    public T first() {
        if (isEmpty()) throw new NoSuchElementException("Set is empty");
        Node<T> curr = root;
        while (curr.left.value != null)
            curr = curr.left;
        return curr.value;
    }

    @Override
    public T last() {
        if (isEmpty()) throw new NoSuchElementException("Set is empty");
        Node<T> cur = root;
        while (cur.right.value != null)
            cur = cur.right;
        return cur.value;
    }

    @Override
    public List<T> inorderTraverse() {
        List<T> list = new ArrayList<T>();
        return inOrder(root, list);
    }

    private List<T> inOrder(Node<T> node, List<T> list) {
        if (root != null) {
            inOrder(node.left, list);
            list.add(node.value);
            inOrder(node.right, list);
        }
        return list;
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
    public boolean add(T value) {
        if (value == null) {
            throw new NullPointerException("null");
        }
        Node<T> Y = nil;
        Node<T> X = root;
        while (X != nil) {
            Y = X;
            Y.left = X.left;
            Y.right = X.right;
            if (value.compareTo(X.value) < 0) X = X.left;
            else if (value.compareTo(X.value) > 0) X = X.right;
            else return false;
        }
        Node<T> Z = new Node<T>(value, Y, nil);
        if (Y == nil) root = Z;
        else if (Z.value.compareTo(Y.value) < 0) {
            Y.left = Z;
        } else {
            Y.right = Z;
        }
        Z.left = nil;
        Z.right = nil;
        Z.color(Color.RED);
        addFixUp(Z);
        size++;
        return true;
    }

    @Override
    public boolean remove(T value) {
        if (value == null) {
            throw new NullPointerException("null");
        }
        T data = (T) value;
        Node<T> currentNode = root;
        if (!contains(value)) return false;
        while (currentNode != nil && currentNode.value.compareTo(value) != 0) {
            if (currentNode.value.compareTo(value) < 0) currentNode = currentNode.right;
            else currentNode = currentNode.left;
        }
        if (currentNode == nil) return false;
        Node<T> Z = currentNode;
        Node<T> Y;
        if (Z.left == nil || Z.right == nil) Y = Z;
        else Y = nextNode(Z);
        Node<T> X = new Node<>();
        if (Y.left != nil) X = Y.left;
        else X = Y.right;
        X.parent = Y.parent;
        if (Y.parent == nil)
            root = X;
        else if (Y == Y.parent.left)
            Y.parent.left = X;
        else Y.parent.right = X;
        if (Y != Z)
            Z.value = Y.value;
        if (Y.color().isBlack)
            advancedTreeRepair(X);
        size--;
        return true;
    }

    private void addFixUp(Node<T> Z) {
        while (Z.parent.color().isRed) {
            if (Z.parent.parent.left == Z.parent) {
                Node<T> Y = Z.parent.parent.right;
                if (Y.color().isRed) {
                    Z.parent.color(Color.BLACK);
                    Y.color(Color.BLACK);
                    Z.parent.parent.color(Color.RED);
                    Z = Z.parent.parent;
                } else {
                    if (Z == Z.parent.right) {
                        Z = Z.parent;
                        turnLeft(Z);
                    }
                    Z.parent.color(Color.BLACK);
                    Z.parent.parent.color(Color.RED);
                    turnRight(Z.parent.parent);
                }
            } else if (Z.parent.parent.right == Z.parent) {
                Node<T> Y = Z.parent.parent.left;
                if (Y.color().isRed) {
                    Z.parent.color(Color.BLACK);
                    Y.color(Color.BLACK);
                    Z.parent.parent.color(Color.RED);
                    Z = Z.parent.parent;
                } else {
                    if (Z == Z.parent.left) {
                        Z = Z.parent;
                        turnRight(Z);
                    }
                    Z.parent.color(Color.BLACK);
                    Z.parent.parent.color(Color.RED);
                    turnLeft(Z.parent.parent);
                }
            }
        }
        root.color(Color.BLACK);
    }

/*
    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {
        T value = (T) o;
        Node<T> currentNode = root;
        if (!contains(o)) return false;
        while (currentNode != nil && currentNode.value.compareTo(value) != 0) {
            if (currentNode.value.compareTo(value) < 0) currentNode = currentNode.right;
            else currentNode = currentNode.left;
        }
        if (currentNode == nil) return false;
        Node<T> Z = currentNode;
        Node<T> Y;
        if (Z.left == nil || Z.right == nil) Y = Z;
        else Y = nextNode(Z);
        Node<T> X = new Node<>();
        if (Y.left != nil) X = Y.left;
        else X = Y.right;
        X.parent = Y.parent;
        if (Y.parent == nil)
            root = X;
        else if (Y == Y.parent.left)
            Y.parent.left = X;
        else Y.parent.right = X;
        if (Y != Z)
            Z.value = Y.value;
        if (Y.color().isBlack)
            advancedTreeRepair(X);
        size--;
        return true;
    }
*/

    private Node<T> nextNode(Node<T> currentNode) {
        if (currentNode.right != nil) {
            return min(currentNode.right);
        }
        Node<T> tempNode = currentNode.parent;
        while (tempNode != nil && currentNode != tempNode.right) {
            currentNode = tempNode;
            tempNode = tempNode.parent;
        }
        return tempNode;
    }

    private void advancedTreeRepair(Node<T> currentNode) {
        Node<T> W;
        while (currentNode != root && currentNode.color().isBlack) {
            if (currentNode == currentNode.parent.left) {
                W = currentNode.parent.right;
                if (W.color().isRed) {
                    W.color(Color.BLACK);
                    currentNode.parent.color(Color.RED);
                    turnLeft(currentNode.parent);
                    W = currentNode.parent.right;
                }
                if (W.left.color().isBlack && W.right.color().isBlack) {
                    W.color(Color.RED);
                    currentNode = currentNode.parent;
                } else {
                    if (W.right.color().isBlack) {
                        W.left.color(Color.BLACK);
                        W.color(Color.RED);
                        turnRight(W);
                        W = currentNode.parent.right;
                    }
                    W.color(currentNode.parent.color());
                    currentNode.parent.color(Color.BLACK);
                    W.right.color(Color.BLACK);
                    turnLeft(currentNode.parent);
                    currentNode = root;
                }
            } else {
                W = currentNode.parent.left;
                if (W.color().isRed) {
                    W.color(Color.BLACK);
                    currentNode.parent.color(Color.RED);
                    turnRight(currentNode.parent);
                    W = currentNode.parent.left;
                }
                if (W.right.color().isBlack && W.left.color().isBlack) {
                    W.color(Color.RED);
                    currentNode = currentNode.parent;
                } else {
                    if (W.left.color().isBlack) {
                        W.right.color(Color.BLACK);
                        W.color(Color.RED);
                        turnLeft(W);
                        W = currentNode.parent.left;
                    }
                    W.color(currentNode.parent.color());
                    currentNode.parent.color(Color.BLACK);
                    W.left.color(Color.BLACK);
                    turnRight(currentNode.parent);
                    currentNode = root;
                }

            }
        }
        currentNode.color(Color.BLACK);
    }

    @Override
    public boolean contains(T value) {
        Node<T> currentNode = root;
        while (currentNode != nil) {
            if (currentNode.value.compareTo(value) > 0)
                currentNode = currentNode.left;
            else if (currentNode.value.compareTo(value) < 0)
                currentNode = currentNode.right;
            else return true;
        }
        return false;
    }

    /*@SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
        T value = (T) o;
        Node<T> currentNode = root;
        while (currentNode != nil) {
            if (currentNode.value.compareTo(value) > 0)
                currentNode = currentNode.left;
            else if (currentNode.value.compareTo(value) < 0)
                currentNode = currentNode.right;
            else return true;
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return new Iterator();
    }

    @Override
    public Object[] toArray() {
        Object[] arrayed = new Object[size];
        int i = 0;
        for (T it : this) {
            arrayed[i] = it;
            i++;
        }
        return arrayed;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length >= size) {
            int i = 0;
            for (T it : this) {
                a[i] = (T1) it;
                i++;
            }
            return a;
        } else {
            T1[] b = (T1[]) new Object[size];
            int i = 0;
            for (T it : this) {
                b[i] = (T1) it;
                i++;
            }
            return b;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object it : c) {
            if (!contains(it)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        int counter = this.size;
        for (Object it : c)
            add((T) it);
        if (this.size > counter) return true;
        else return false;
    }

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

    @Override
    public boolean retainAll(Collection<?> c) {
        int counter = this.size;
        ArrayList list = new ArrayList();
        for (T it : this)
            if (!c.contains(it))
                list.add(it);
        removeAll(list);
        return this.size < counter;
    }

    @Override
    public void clear() {
        this.forEach(this::remove);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof RedBlackTree) {
            RedBlackTree<T> tree = (RedBlackTree<T>) o;
            return (size == tree.size && containsAll(tree));
        }
        return false;
    }*/

    public int hashcode() {
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
        result = 26 * result + (stringValue == null ? 0 : stringValue.hashCode());
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

    public enum Color {
        BLACK(false),
        RED(true);
        public final boolean isRed;
        public final boolean isBlack;

        private Color(boolean value) {
            this.isRed = value;
            this.isBlack = !value;
        }

        public Color not() {
            return this.isRed ? BLACK : RED;
        }
    }

    class Node<T extends Comparable<T>> {
        public Node<T> right;
        public Node<T> left;
        public Node<T> parent;
        public T value;
        private Color color;

        public Node(T value, Node<T> parent, Node<T> nil) {
            this.value = value;
            this.parent = parent;
            if (parent != nil) {
                this.color = Color.RED;
                if (this.value.compareTo(parent.value) > 0)
                    parent.right = this;
                else
                    parent.left = this;
            } else
                this.color = Color.BLACK;
        }

        public Node() {
            this.value = null;
            parent = this;
            left = this;
            right = this;
            color = Color.BLACK;
        }

        public Color color() {
            return (value == null) ? Color.BLACK : color;
        }

        public void color(Color color) {
            this.color = color;
        }

        public int hashcode() {
            int result = 26;
            result *= 31 + color.hashCode();
            result *= 31 + value.hashCode();
            return result;
        }
    }

    public class Iterator implements java.util.Iterator<T> {
        private Node<T> it = nil;
        private Stack<Node<T>> stack = new Stack<>();

        public Iterator() {
            it = root;
            if (it == nil) return;
            stack.push(nil);
            while (it.left != nil) {
                stack.push(it);
                it = it.left;
            }
        }

        public Color color() {
            return it.color();
        }

        @Override
        public boolean hasNext() {
            return it != nil;
        }

        @Override
        public T next() {
            T val;
            if (it != nil)
                val = it.value;
            else throw new NoSuchElementException();
            if (it.right != nil) {
                it = it.right;
                while (it.left != nil) {
                    stack.push(it);
                    it = it.left;
                }
            } else it = stack.pop();
            return val;
        }
    }
}