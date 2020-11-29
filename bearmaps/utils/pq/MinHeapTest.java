package bearmaps.utils.pq;
import org.junit.Test;


class MinHeapTest {
    @Test
    public void MinHeapTest() {

    }


    @org.junit.jupiter.api.Test
    void findMin() {
        MinHeap<Integer> tester = new MinHeap<>();
        tester.size();
        for (int i = 0; i < 10; i++) {
            tester.insert(i);
            System.out.println(tester.size());
        }
        tester.removeMin();
        System.out.println(tester.findMin());
        System.out.println(tester.findMin());
    }

    @org.junit.jupiter.api.Test
    void size() {
    }

    @org.junit.jupiter.api.Test
    void insert() {
        MinHeap<Integer> pq = new MinHeap<>();
        pq.insert(0);
        pq.insert(4);
        pq.insert(3);
        pq.insert(9);
        System.out.println(pq.getContents());
        pq.insert(1);
        pq.insert(8);
        pq.removeMin();
        pq.insert(5);
        pq.insert(6);
        pq.removeMin();
        pq.insert(2);
        System.out.println(pq.getContents());
    }

    @org.junit.jupiter.api.Test
    void removeMin() {
    }

    @org.junit.jupiter.api.Test
    void update() {

    }

    @org.junit.jupiter.api.Test
    void contains() {
        MinHeap<Integer> tester = new MinHeap<>();
        tester.size();
        for (int i = 1; i < 10; i++) {
            tester.insert(i);
            System.out.println(tester.size());
        }
        System.out.println(tester.contains(1));
        System.out.println(tester.contains(6));
    }
}