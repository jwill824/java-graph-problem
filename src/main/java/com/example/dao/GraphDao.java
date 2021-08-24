package com.example.dao;

import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Component;

@Component
public class GraphDao {
  private Graph<String, DefaultWeightedEdge> graph;
  private AllDirectedPaths<String, DefaultWeightedEdge> directedPaths;

  public GraphDao(final Graph<String, DefaultWeightedEdge> graph) {
    this.graph = graph;
    this.directedPaths = new AllDirectedPaths<String, DefaultWeightedEdge>(graph);
  }

  public String findTotalDistanceOfPath(final List<String> path) {
    int totalDistance = 0;

    List<String> route = path;
    String node = route.get(0);
    route.remove(0);

    for (String nextNode : route) {
      DefaultWeightedEdge edge = graph.getEdge(node, nextNode);

      if (edge == null) {
        return "NO SUCH ROUTE";
      }

      totalDistance += graph.getEdgeWeight(edge);
      node = nextNode;
    }

    return Integer.toString(totalDistance);
  }

  public Integer findShortestDistanceBetweenTwoNodes(
      final String sourceNode, final String targetNode) {
    if (sourceNode.equals(targetNode)) {
      return directedPaths.getAllPaths(sourceNode, targetNode, false, 20).stream()
          .filter(graphPath -> graphPath.getLength() > 1)
          .mapToInt(v -> (int) v.getWeight())
          .min()
          .getAsInt();
    }

    return (int) DijkstraShortestPath.findPathBetween(graph, sourceNode, targetNode).getWeight();
  }

  public Integer findNumberOfPathsBetweenTwoNodesGivenDistance(
      final String sourceNode, final String targetNode, final int distance, final int comparator) {
    List<GraphPath<String, DefaultWeightedEdge>> graphPaths =
        directedPaths.getAllPaths(sourceNode, targetNode, false, 20);

    Integer result = 0;

    switch (comparator) {
      case 0: // equals
        result =
            (int)
                graphPaths.stream().filter(graphPath -> graphPath.getWeight() == distance).count();
        break;
      case 1: // less than
        result =
            (int)
                graphPaths.stream()
                    .filter(
                        graphPath -> graphPath.getLength() > 1 && graphPath.getWeight() < distance)
                    .count();
        break;
      case 2: // less than or equal to
        result =
            (int)
                graphPaths.stream()
                    .filter(
                        graphPath -> graphPath.getLength() > 1 && graphPath.getWeight() <= distance)
                    .count();
        break;
    }

    return result;
  }

  public Integer findNumberOfPathsBetweenTwoNodesGivenStops(
      final String sourceNode, final String targetNode, final int stops, final int comparator) {
    List<GraphPath<String, DefaultWeightedEdge>> graphPaths =
        directedPaths.getAllPaths(sourceNode, targetNode, false, stops);

    Integer result = 0;

    switch (comparator) {
      case 0: // equals
        result =
            (int) graphPaths.stream().filter(graphPath -> graphPath.getLength() == stops).count();
        break;
      case 1: // less than
        result =
            (int)
                graphPaths.stream()
                    .filter(graphPath -> graphPath.getLength() > 1 && graphPath.getLength() < stops)
                    .count();
        break;
      case 2: // less than or equal to
        result =
            (int)
                graphPaths.stream()
                    .filter(
                        graphPath -> graphPath.getLength() > 1 && graphPath.getLength() <= stops)
                    .count();
        break;
    }

    return result;
  }
}
