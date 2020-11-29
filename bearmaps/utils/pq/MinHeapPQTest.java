package bearmaps.utils.pq;

import bearmaps.utils.ps.Point;
import edu.princeton.cs.algs4.In;
import org.junit.Test;
//import Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapPQTest {


    @Test
    void peek() {
        PriorityQueue<Integer> pq = new MinHeapPQ<>();
        List<PriorityItem<Integer>> items = generateItems(10);
        double minPriority = Double.POSITIVE_INFINITY;
        for (PriorityItem<Integer> item : items) {
            pq.insert(item.item, item.priorityValue);
            minPriority = Math.min(minPriority, item.priorityValue);
        }
        pq.insert(1, minPriority - 1.0);
        assertEquals(1, pq.peek());
    }


    @Test
    void insertAndRemove() {
        PriorityQueue<Integer> pq = new MinHeapPQ<>();
        for (int i = 0; i < 100; i++) {
            pq.insert(i, (double) i);
        }
        double prev = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 100; i++) {
            Integer polled = pq.poll();
            assertEquals(i, polled);
        }
    }

    @Test
    void changePriority() {
        PriorityQueue<Integer> pq = new MinHeapPQ<>();
        final int numItems = 100;
        for (int i = 1; i <= numItems; i++) {
            pq.insert(i, (double) i);
        }
        pq.changePriority(5, 0);
        pq.changePriority(1, 100);
        System.out.println(pq.toString());
    }

    @Test
    void size() {
        PriorityQueue<Integer> pq = new MinHeapPQ<>();
        List<PriorityItem<Integer>> items = generateItems(10);
        for (PriorityItem<Integer> item : items) {
            pq.insert(item.item, item.priorityValue);
        }
        assertEquals(10, pq.size());
    }

    @Test
    void contains() {
        PriorityQueue<Integer> pq = new MinHeapPQ<>();
        List<PriorityItem<Integer>> items = generateItems(1000);
        for (PriorityItem<Integer> item : items) {
            pq.insert(item.item, item.priorityValue);
        }
        for (PriorityItem<Integer> item : items) {
            assertTrue(pq.contains(item.item));
        }
    }

    public class PriorityItem<T> implements Comparable<PriorityItem<T>> {
        private T item;
        private double priorityValue;

        private PriorityItem(T item, double priorityValue) {
            this.item = item;
            this.priorityValue = priorityValue;
        }

        public T item() {
            return this.item;
        }

        public double priorityValue() {
            return this.priorityValue;
        }

        @Override
        public String toString() {
            return "(PriorityItem: " + this.item.toString() + ", "
                    + this.priorityValue + ")";
        }

        @Override
        public int compareTo(PriorityItem o) {
            double diff = this.priorityValue - o.priorityValue;
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            } else {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            } else if (getClass() == o.getClass()) {
                PriorityItem p = (PriorityItem) o;
                return p.item.equals(item);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

    private List<PriorityItem<Integer>> generateItems(int num) {
        Random rng = new Random();
        List<PriorityItem<Integer>> result = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            result.add(new PriorityItem<>(rng.nextInt(), rng.nextDouble()));
        }
        return result;
    }
}