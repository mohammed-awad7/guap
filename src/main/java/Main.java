import java.util.Random;

public class Main {

    public static void main(String[] args) {
        SkipList<Integer> sl = new SkipList<>();
        int[] data = {1,2,3,4,5};
        for (int i : data) {
            sl.insert(i);
        }
        sl.search(10);
    }
}

interface SkippableList<Integer extends Comparable<? super Integer>> {
    int LEVELS = 4;

    boolean delete(Integer target);
    void print();
    void insert(Integer data);
    SkipNode<Integer> search(Integer data);
}

class SkipList<T extends Comparable<? super T>> implements SkippableList<T> {

    private final SkipNode<T> head = new SkipNode<>(null);
    private final Random rand = new Random();

    @Override
    public void insert(T data) {
        SkipNode<T> SkipNode = new SkipNode<>(data);
        for (int i = 0; i < LEVELS; i++) {
            if (rand.nextInt((int) Math.pow(2, i)) == 0) { //insert with prob = 1/(2^i)
                insert(SkipNode, i);
            }
        }
    }

    @Override
    public SkipNode<T> search(T data) {
        return search(data, true);
    }

    @Override
    public void print() {
        for (int i = 0; i < LEVELS; i++) {
            head.print(i);
        }

        System.out.println();
    }
    
    @Override
    public boolean delete(T target) {
    System.out.println("Deleting " + target.toString());
    SkipNode<T> victim = search(target, false);
    if (victim == null) return false;
    victim.data = null;
    for (int i = 0; i < LEVELS; i++) {
        head.refreshAfterDelete(i);
    }
    System.out.println();
    return true;
    }

    private void insert(SkipNode<T> SkipNode, int level) {
        head.insert(SkipNode, level);
    }

    private SkipNode<T> search(T data, boolean print) {
        SkipNode<T> result = null;
        for (int i = LEVELS-1; i >= 0; i--) {
            if ((result = head.search(data, i, print)) != null) {
                if (print) {
                    System.out.println("Found " + data.toString() + " at level " + i + ", so stoppped");
                    System.out.println();
                }

                break;
            }
        }

        return result;
    }

}

class SkipNode<N extends Comparable<? super N>> {

    N data;
    @SuppressWarnings("unchecked")
    SkipNode<N>[] next = (SkipNode<N>[]) new SkipNode[SkippableList.LEVELS];

    SkipNode(N data) {
        this.data = data;
    }

    void refreshAfterDelete(int level) {
        SkipNode<N> current = this.getNext(level);
        while (current != null && current.getNext(level) != null) {
            if (current.getNext(level).data == null) {
                SkipNode<N> successor = current.getNext(level).getNext(level);
                current.setNext(successor, level);
                return;
            }

            current = current.getNext(level);
        }
    }

    void setNext(SkipNode<N> next, int level) {
        this.next[level] = next;
    }

    SkipNode<N> getNext(int level) {
        return this.next[level];
    }

    SkipNode<N> search(N data, int level, boolean print) {
        if (print) {
            System.out.print("Searching for: " + data + " at ");
            print(level);
        }

        SkipNode<N> result = null;
        SkipNode<N> current = this.getNext(level);
        while (current != null && current.data.compareTo(data) < 1) {
            if (current.data.equals(data)) {
                result = current;
                break;
            }

            current = current.getNext(level);
        }

        return result;
    }

    void insert(SkipNode<N> SkipNode, int level) {
        SkipNode<N> current = this.getNext(level);
        if (current == null) {
            this.setNext(SkipNode, level);
            return;
        }

        if (SkipNode.data.compareTo(current.data) < 1) {
            this.setNext(SkipNode, level);
            SkipNode.setNext(current, level);
            return;
        }

        while (current.getNext(level) != null && current.data.compareTo(SkipNode.data) < 1 &&
                current.getNext(level).data.compareTo(SkipNode.data) < 1) {

            current = current.getNext(level);
        }

        SkipNode<N> successor = current.getNext(level);
        current.setNext(SkipNode, level);
        SkipNode.setNext(successor, level);
    }

    void print(int level) {
        System.out.print("level " + level + ": [");
        int length = 0;
        SkipNode<N> current = this.getNext(level);
        while (current != null) {
            length++;
            System.out.print(current.data.toString() + " ");
            current = current.getNext(level);
        }

        System.out.println("], length: " + length);
    }
}
