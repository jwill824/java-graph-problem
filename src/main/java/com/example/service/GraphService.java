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
    if (source.equals(target)) {
      return searchMinDistanceLoop(Integer.MAX_VALUE, source, target, 0);
    }
    return searchMinDistance(Integer.MAX_VALUE, source, target, 0);
  }

  public Integer findNumberOfPathsBetweenTwoNodesGivenDistance(
      final String sourceNode, final String targetNode, final int distance, final int comparator) {
    Node source = graph.get(sourceNode);
    Node target = graph.get(targetNode);
    return searchDistance(0, source, target, 0, distance, comparator);
  }

  public Integer findNumberOfPathsBetweenTwoNodesGivenStops(
      final String sourceNode, final String targetNode, final int stops, final int comparator) {
    Node source = graph.get(sourceNode);
    Node target = graph.get(targetNode);
    return searchStops(0, source, target, 0, stops, comparator);
  }

  private Integer searchMinDistance(
      Integer minDistance, Node source, Node target, Integer distance) {
    for (Edge edge : source.getNeighbors()) {
      Node neighbor = edge.getTarget();
      int total = distance + edge.getWeight();

      if (source.equals(target)) {
        break;
      }

      if (neighbor.equals(target) && total < minDistance) {
        minDistance = total;
        break;
      }

      minDistance = searchMinDistance(minDistance, neighbor, target, total);
    }

    return minDistance;
  }

  private Integer searchMinDistanceLoop(
      Integer minDistance, Node source, Node target, Integer distance) {
    for (Edge edge : source.getNeighbors()) {
      Node neighbor = edge.getTarget();
      int total = distance + edge.getWeight();

      if (neighbor.equals(target) && total < minDistance) {
        minDistance = total;
        break;
      }

      minDistance = searchMinDistanceLoop(minDistance, neighbor, target, total);
    }

    return minDistance;
  }

  private Integer searchStops(
      int count, Node source, Node target, Integer stops, Integer maxStops, Integer comparator) {
    for (Edge edge : source.getNeighbors()) {
      Node neighbor = edge.getTarget();
      int total = stops + 1;

      if (total > maxStops) {
        break;
      }

      boolean compare = false;

      switch (comparator) {
        case 0:
          compare = total == maxStops;
          break;
        case 1:
          compare = total < maxStops;
          break;
        case 2:
          compare = total <= maxStops;
          break;
      }

      if (neighbor.equals(target) && compare) {
        count++;
      }

      count = searchStops(count, neighbor, target, total, maxStops, comparator);
    }

    return count;
  }

  private Integer searchDistance(
      int count,
      Node source,
      Node target,
      Integer distance,
      Integer maxDistance,
      Integer comparator) {
    for (Edge edge : source.getNeighbors()) {
      Node neighbor = edge.getTarget();
      int total = distance + edge.getWeight();

      if (total > maxDistance) {
        break;
      }

      boolean compare = false;

      switch (comparator) {
        case 0:
          compare = total == maxDistance;
          break;
        case 1:
          compare = total < maxDistance;
          break;
        case 2:
          compare = total <= maxDistance;
          break;
      }

      if (neighbor.equals(target) && compare) {
        count++;
      }

      count = searchDistance(count, neighbor, target, total, maxDistance, comparator);
    }

    return count;
  }
}
