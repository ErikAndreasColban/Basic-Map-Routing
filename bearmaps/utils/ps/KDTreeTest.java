package bearmaps.utils.ps;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    @Test
    public void KDTreeTest() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100; i++) {

            Random rng = new Random();
            points.add(new Point(rng.nextDouble(), rng.nextDouble()));
        }
        KDTree tester = new KDTree(points);
        for (int i = 0; i < 100; i++) {
            Random rng = new Random();
            double one = rng.nextDouble();
            double two = rng.nextDouble();
            tester.nearest(one, two);
        }

        /**
        Point p0 = new Point(0, 0);
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point(1.2, 2.0);
        List<Point> points = new ArrayList<>();
        points.add(p0);
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.sort(Comparator.comparing(Point::getX));
        System.out.println(points.toString());
        points.sort(Comparator.comparing(Point::getY));
        System.out.println(points.toString());
        KDTree tester = new KDTree(points);
        tester.nearest(1.15, 2.0);
        System.out.println(tester.nearest(1.15, 2.0)); */

    }

}