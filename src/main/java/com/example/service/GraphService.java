package com.example.service;

import com.example.model.Edge;
import com.example.model.Node;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GraphService {
  private Map<String, Node> graph;

  public GraphService(final Map<String, Node> graph) {
    this.graph = graph;
  }

  public String findTotalDistanceOfPath(final List<String> path) {
    int totalDistance = 0;

    List<Node> nodes = path.stream().map(node -> graph.get(node)).collect(Collectors.toList());

    for (int i = 0; i < path.size() - 1; i++) {
      try {
        totalDistance += nodes.get(i).getWeight(nodes.get(i + 1));
      } catch (NoSuchElementException ex) {
        return ex.getMessage();
      }
    }

    return String.valueOf(totalDistance);
  }

  public Integer findShortestDistanceBetweenTwoNodes(
      final String sourceNode, final String targetNode) {
    Node source = graph.get(sourceNode);
    Node target = graph.get(targetNode);
    return searchMinDistance(Integer.MAX_VALUE, 0, 0, source, target);
  }

  public Integer findNumberOfPathsBetweenTwoNodesGivenDistance(
      final String sourceNode,
      final String targetNode,
      final int maxDistance,
      final int comparator) {
    Node source = graph.get(sourceNode);
    Node target = graph.get(targetNode);
    return searchDistance(0, source, target, 0, 0, maxDistance, comparator);
  }

  public Integer findNumberOfPathsBetweenTwoNodesGivenStops(
      final String sourceNode, final String targetNode, final int maxStops, final int comparator) {
    Node source = graph.get(sourceNode);
    Node target = graph.get(targetNode);
    return searchStops(0, source, target, 0, 0, maxStops, comparator);
  }

  private Integer searchMinDistance(
      Integer minDistance, Integer currDistance, Integer prevDistance, Node source, Node target) {

    if (source.getNeighbors().size() > 1) {
      prevDistance = currDistance;
    }

    source.shiftEdgesToTarget(target);

    for (Edge edge : source.getNeighbors()) {
      Node neighbor = edge.getTarget();

      if (currDistance > minDistance) {
        break;
      }

      currDistance += source.getWeight(neighbor);

      if (neighbor.equals(target)) {
        minDistance = Math.min(currDistance, minDistance);
        break;
      }

      minDistance = searchMinDistance(minDistance, currDistance, prevDistance, neighbor, target);

      currDistance = prevDistance;
    }

    return minDistance;
  }

  private Integer searchStops(
      int count,
      Node source,
      Node target,
      Integer currStops,
      Integer prevStops,
      Integer maxStops,
      Integer comparator) {
    if (source.getNeighbors().size() > 1) {
      prevStops = currStops;
    }

    source.shiftEdgesToTarget(target);

    for (Edge edge : source.getNeighbors()) {
      Node neighbor = edge.getTarget();

      if (currStops > maxStops) {
        break;
      }

      currStops++;

      boolean compare = false;

      switch (comparator) {
        case 0:
          compare = currStops.equals(maxStops);
          break;
        case 1:
          compare = currStops < maxStops;
          break;
        case 2:
          compare = currStops <= maxStops;
          break;
      }

      if (neighbor.equals(target) && compare) {
        count++;
      }

      count = searchStops(count, neighbor, target, currStops, prevStops, maxStops, comparator);

      currStops = prevStops;
    }

    return count;
  }

  private Integer searchDistance(
      int count,
      Node source,
      Node target,
      Integer currDistance,
      Integer prevDistance,
      Integer maxDistance,
      Integer comparator) {
    if (source.getNeighbors().size() > 1) {
      prevDistance = currDistance;
    }

    source.shiftEdgesToTarget(target);

    for (Edge edge : source.getNeighbors()) {
      Node neighbor = edge.getTarget();

      if (currDistance > maxDistance) {
        break;
      }

      currDistance += source.getWeight(neighbor);

      boolean compare = false;

      switch (comparator) {
        case 0:
          compare = currDistance.equals(maxDistance);
          break;
        case 1:
          compare = currDistance < maxDistance;
          break;
        case 2:
          compare = currDistance <= maxDistance;
          break;
      }

      if (neighbor.equals(target) && compare) {
        count++;
      }

      count =
          searchDistance(
              count, neighbor, target, currDistance, prevDistance, maxDistance, comparator);

      currDistance = prevDistance;
    }

    return count;
  }
}
