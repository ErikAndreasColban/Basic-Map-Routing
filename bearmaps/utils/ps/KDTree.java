package bearmaps.utils.ps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import bearmaps.AugmentedStreetMapGraph;

public class KDTree implements PointSet{

    private Point root;
    private KDTree left;
    private KDTree right;

    public KDTree(Point point) {
        root = point;
        left = null;
        right = null;
    }
    public KDTree() {
        root = null;
        left = null;
        right = null;
    }
    public KDTree(List<Point> points) {

        ArrayList<Point> sortedByX = new ArrayList<>();
        ArrayList<Point> sortedByY = new ArrayList<>();
        sortedByY.addAll(points);
        sortedByX.addAll(points);
        sortedByY.sort(compareXorY(true));
        sortedByY.sort(compareXorY(false));
        sortedByX.sort(compareXorY(false));
        sortedByX.sort(compareXorY(true));

        this.KDTreeHelper(sortedByX, sortedByY, true);
    }
    public void KDTreeHelper(List<Point> byX, List<Point> byY, boolean onX) {
        if (onX) {
            if (byX.size() == 0) {
                return;
            }
            if (byX.size() == 1) {
                root = byX.get(0);
                return;
            }
            int half = (byX.size()) / 2;
            Point median = byX.get(half);
            root = median;
            left = new KDTree();
            left.KDTreeHelper(filterLesserX(byX, median), filterLesserX(byY, median), !onX);
            if (byX.size() > 2) {
                right = new KDTree();
                right.KDTreeHelper(filterGreaterX(byX, median), filterGreaterX(byY, median), !onX);
            }
        }
        else {
            if (byY.size() == 0) {
                return;
            }
            if (byY.size() == 1) {
                root = byY.get(0);
                return;
            }
            int half = (byY.size()) / 2;
            Point median = byY.get(half);
            root = median;
            left = new KDTree();
            left.KDTreeHelper(filterLesserY(byX, median), filterLesserY(byY, median), !onX);
            if (byY.size() > 2) {
                right = new KDTree();
                right.KDTreeHelper(filterGreaterY(byX, median), filterGreaterY(byY, median), !onX);
            }
        }
        }
    private static double distance1(double lonV, double lonW, double latV, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }
    public static double distance1(Point p1, Point p2) {
        return distance1(p1.getX(), p2.getX(), p1.getY(), p2.getY());
    }

    public Comparator<Point> compareXorY(boolean onX) {
        if (onX) {
            return Comparator.comparing(Point::getX);
        }
        return Comparator.comparing(Point::getY);
    }

    private List<Point> filterGreaterX(List<Point> byXorY, Point median) {
        return byXorY.stream().filter(point -> ((point.getX() > median.getX()) || (point.getY() > median.getY() && point.getX() == median.getX()))).collect(Collectors.toList());
    }
    private List<Point> filterGreaterY(List<Point> byXorY, Point median) {
        return byXorY.stream().filter(point -> ((point.getY() > median.getY()) || (point.getX() > median.getX() && point.getY() == median.getY()))).collect(Collectors.toList());
    }
    private List<Point> filterLesserX(List<Point> byXorY, Point median) {
        return byXorY.stream().filter(point -> ((point.getX() < median.getX()) || (point.getY() < median.getY() && point.getX() == median.getX()))).collect(Collectors.toList());
    }
    private List<Point> filterLesserY(List<Point> byXorY, Point median) {
        return byXorY.stream().filter(point -> ((point.getY() < median.getY()) || (point.getX() < median.getX() && point.getY() == median.getY()))).collect(Collectors.toList());
    }

    public Point nearest1(double x, double y) {
        Point point = new Point(x, y);
        return this.nearestHelper1(point , root, true);
    }
    public Point nearestHelper1(Point point, Point best, boolean onX) {
        if (point.getX() == this.root.getX() && point.getY() == this.root.getY()) {
            return this.root;
        }
        if (distance1(point, this.root) < distance1(point, best)) {
            best = this.root;
        }
        if (compareXorY(onX).compare(point, this.root) < 0) {
            if (left != null) {
                best =  left.nearestHelper1(point, best, !onX);
            }
            if (right != null) {
                if (onX) {
                    if (point.getX() - root.getX() < distance1(point, best)) {
                        best = right.nearestHelper1(point, best, !onX);
                    }
                } else if (point.getY() - root.getY() < distance1(point, best)) {
                    best = right.nearestHelper1(point, best, !onX);
                }
            }
        }
        else {
            if (right != null) {
                best =  right.nearestHelper1(point, best, !onX);
            }
            if (left != null) {
                if (onX) {
                    if (point.getX() - root.getX() < distance1(point, best)) {
                        best = left.nearestHelper1(point, best, !onX);
                    }
                } else if (point.getY() - root.getY() < distance1(point, best)) {
                    best = left.nearestHelper1(point, best, !onX);
                }
            }
        }
        return best;
    }

    @Override
    public Point nearest(double x, double y) {
        Point point = new Point(x, y);
        return this.nearestHelper(point , root, true);
    }
    public Point nearestHelper(Point point, Point best, boolean onX) {
        if (point.getX() == this.root.getX() && point.getY() == this.root.getY()) {
            return this.root;
        }
        if (Point.distance(point, this.root) < Point.distance(point, best)) {
            best = this.root;
        }

        if (compareXorY(onX).compare(point, this.root) < 0) {
            if (left != null) {
                best =  left.nearestHelper(point, best, !onX);
            }
            if (right != null) {
                if (onX) {
                    if (point.getX() - root.getX() < Point.distance(point, best)) {
                        best = right.nearestHelper(point, best, !onX);
                    }
                } else if (point.getY() - root.getY() < Point.distance(point, best)) {
                    best = right.nearestHelper(point, best, !onX);
                }
            }
        }
        else {
            if (right != null) {
                best =  right.nearestHelper(point, best, !onX);
            }
            if (left != null) {
                if (onX) {
                    if (point.getX() - root.getX() < Point.distance(point, best)) {
                        best = left.nearestHelper(point, best, !onX);
                    }
                } else if (point.getY() - root.getY() < Point.distance(point, best)) {
                    best = left.nearestHelper(point, best, !onX);
                }
            }
            }
        return best;
    }

}
