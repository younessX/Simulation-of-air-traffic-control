package prototype;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.lang.String;

public class Node implements Comparable<Node> {

	private final String name;
	private double distance = Integer.MAX_VALUE;
	private List<Node> shortestPath = new LinkedList<>();
	private Map<Node, Double> adjacentNodes = new HashMap<>();

	public Node(String name) {
		this.name =  name;
	}

	public void addAdjacentNode(Node node, double weight) {
		adjacentNodes.put(node, weight);
	}

	public List<Node> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public Map<Node, Double> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setAdjacentNodes(Map<Node, Double> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}

	public String getName() {
		return name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(Node node) {
		return Double.compare(this.distance, node.getDistance());
	}

}