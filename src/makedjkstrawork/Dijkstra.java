/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makedjkstrawork;

import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Hallur
 */
public class Dijkstra {

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {

        source.setDistance(0.0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Entry<Node, Double> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Double edgeWeigh = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Double edgeWeigh, Node sourceNode) {
        Double sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        Double lowestDistance = Double.MAX_VALUE;
        for (Node node : unsettledNodes) {
            Double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    public static void main(String[] args) {
        In in = new In("C:\\Users\\Hallur\\Documents\\NetBeansProjects\\MakeDjkstraWork\\src\\makedjkstrawork\\airports.txt");
        In in2 = new In("C:\\Users\\Hallur\\Documents\\NetBeansProjects\\MakeDjkstraWork\\src\\makedjkstrawork\\routes.txt");

        Graph graph = new Graph();
        runDemo(graph);
        //runFiles(in.readAllLines(),in2.readAllLines(), graph);
        printShortestPaths(graph);

    }

    public static void runFiles(String[] airportsArr, String[] routesArr, Graph graph) {
        /*for (int i = 2; i < airportsArr.length; i++) {
            Node node = new Node(airportsArr[1].split(";")[0]);
            Node dummyNode = new Node("Hallur");
            node.addDestination(dummyNode, 123.32112);
            graph.addNode(node);
        }
        Node firstNote = null;
        for (Node node : graph.getNodes()) {
            firstNote = node;
            break;
        }
        graph = Dijkstra.calculateShortestPathFromSource(graph, firstNote);*/
    }

    public static void runDemo(Graph graph) {
        Node nodeA = new Node("AER");
        Node nodeB = new Node("BDS");
        Node nodeC = new Node("CZX");
        Node nodeD = new Node("DVD");
        Node nodeE = new Node("ERE");
        Node nodeF = new Node("FGG");

        nodeA.addDestination(nodeB, 10.0);
        nodeA.addDestination(nodeC, 15.0);

        nodeB.addDestination(nodeD, 12.0);
        nodeB.addDestination(nodeF, 15.0);

        nodeC.addDestination(nodeE, 10.0);

        nodeD.addDestination(nodeE, 2.0);
        nodeD.addDestination(nodeF, 1.0);

        nodeF.addDestination(nodeE, 5.0);

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);
    }

    public static void printShortestPaths(Graph graph) {
        for (Node node : graph.getNodes()) {
            System.out.print("from AER to " + node.getName() + " = " + node.getDistance());
            System.out.println();
        }

        for (Node node : graph.getNodes()) {
            System.out.print("shortest path to " + node.getName() + " from A = ");
            for (Node node2 : node.getShortestPath()) {
                if (node2.getName().equals("AER")) {
                    System.out.print(node2.getName() + "->");
                } else {
                    System.out.print(node2.getName() + "(" + node2.getDistance() + ")->");
                }

            }
            System.out.print(node.getName() + "(" + node.getDistance() + ")");
            System.out.println();
        }
    }
}
