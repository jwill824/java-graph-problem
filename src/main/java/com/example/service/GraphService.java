package com.example.service;

import com.beust.jcommander.internal.Lists;
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
      Integer minDistance, Integer currDistance, Integer prevDistance, Node source, Node target) {

    if (source.getNeighbors().size() > 1) {
      prevDistance = currDistance;
    }

    // re-arrange neighbors list to add node that matches target first
    if (source.getNeighbors().stream().anyMatch(e -> e.getTarget().equals(target))) {
      Edge edge =
          source.getNeighbors().stream()
              .filter(e -> e.getTarget().equals(target))
              .findFirst()
              .get();
      List<Edge> others =
          source.getNeighbors().stream()
              .filter(e -> !e.getTarget().equals(target))
              .collect(Collectors.toList());
      List<Edge> edges = Lists.newArrayList();
      edges.add(edge);
      edges.addAll(others);
      source.setNeighbors(edges);
    }

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
