package bearmaps.utils.graph;

import bearmaps.utils.graph.AStarGraph;
import bearmaps.utils.graph.ShortestPathsSolver;
import bearmaps.utils.graph.SolverOutcome;
import bearmaps.utils.graph.streetmap.Node;
import bearmaps.utils.graph.streetmap.StreetMapGraph;
import bearmaps.utils.pq.MinHeapPQ;
import edu.princeton.cs.algs4.Stopwatch;
import bearmaps.utils.pq.MinHeapPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private int explored;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch watch = new Stopwatch();
        solution = new ArrayList<>();
        HashMap<Vertex, Double> distTo = new HashMap<>();
        HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
        MinHeapPQ<Vertex> PQ = new MinHeapPQ<>();
        distTo.put(start, 0.0);
        //PQ.insert(start, distTo.get(start) + input.estimatedDistanceToGoal(start, end)); //using AStarGraph
        PQ.insert(start, 0);
        while (PQ.size() != 0 && !PQ.peek().equals(end)) {
            Vertex current = PQ.poll();
            explored += 1;
            Vertex neighborNode;
            double weight;

            for (WeightedEdge<Vertex> neighbor : input.neighbors(current)) {
                /** if (watch.elapsedTime() >= timeout) {
                 outcome = SolverOutcome.TIMEOUT;
                 solution.clear();
                 solutionWeight = 0;
                 timeSpent = timeout;
                 break;
                 } */
//                else {
//                    current = neighbor.from();
                neighborNode = neighbor.to();
                weight = neighbor.weight();

                if (distTo.get(neighborNode) == null || distTo.get(current) + weight < distTo.get(neighborNode)) {
                    edgeTo.put(neighborNode, current);
                    distTo.put(neighborNode, distTo.get(current) + weight);
                    if (PQ.contains(neighborNode)) {
                        PQ.changePriority(neighborNode, distTo.get(neighborNode) + input.estimatedDistanceToGoal(neighborNode, end));
                    } else {
                        PQ.insert(neighborNode, distTo.get(neighborNode) + input.estimatedDistanceToGoal(neighborNode, end));
                    }
                }
                // }
            }
        }

        if (PQ.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
            Vertex curr = end;
            while (curr != null) {
                solution.add(0, curr);
                curr = edgeTo.get(curr);
            }
            //solution.clear();
            solutionWeight = 0;
        } else if (PQ.peek().equals(end)) {
            Vertex curr = end;
            while (curr != null) {
                solution.add(0, curr);
                curr = edgeTo.get(curr);
            }
            outcome = SolverOutcome.SOLVED;
            solutionWeight = distTo.get(PQ.poll());
        }
        timeSpent = watch.elapsedTime();

    }

//        Create a PQ where each vertex v will have priority value p equal to the sum of v’s distance from the source plus the heuristic estimate from v to the goal, i.e. p = distTo[v] + h(v, goal).
//        Insert the source vertex into the PQ. - done
//        Repeat while the PQ is not empty, PQ.peek() is not the goal, and timeout is not exceeded:
//        p = PQ.poll()
//        relax all edges outgoing from p
//        And where the relax method pseudocode is given as below:
//
//        relax(e):
//        p = e.from(), q = e.to(), w = e.weight()
//        if distTo[p] + w < distTo[q]:
//        distTo[q] = distTo[p] + w
//        if q is in the PQ: PQ.changePriority(q, distTo[q] + h(q, goal))


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
        return explored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}