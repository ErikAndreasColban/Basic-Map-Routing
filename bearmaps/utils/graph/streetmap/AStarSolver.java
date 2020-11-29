package bearmaps.utils.graph.streetmap;

import bearmaps.utils.graph.AStarGraph;
import bearmaps.utils.graph.ShortestPathsSolver;
import bearmaps.utils.graph.SolverOutcome;
import org.apache.commons.math3.geometry.spherical.twod.Vertex;
import edu.princeton.cs.algs4.Stopwatch;
import bearmaps.utils.pq.MinHeapPQ;
import bearmaps.utils.pq.MinHeapPQ;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSec; //time in seconds
    private Map<Vertex, Double> distTo;
    private Map<Vertex, Vertex> edgeTo;
    private int numberOfStates;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch(); // time
        boolean reachGoal = false;
        solutionWeight = 0.0;
        numberOfStates = 0;
        solution = new LinkedList<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();

    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numberOfStates - 1;
    }

    @Override
    public double explorationTime() {
        return timeSec;
    }
}
