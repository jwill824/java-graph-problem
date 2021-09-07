package com.example.model;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node {

  private String name;
  private List<Edge> neighbors;

  public Node(final String name) {
    this.name = name;
    this.neighbors = Lists.newArrayList();
  }

  public void shiftEdgesToTarget(final Node target) {
    if (neighbors.stream().anyMatch(e -> e.getTarget().equals(target))) {
      Edge edge = neighbors.stream().filter(e -> e.getTarget().equals(target)).findFirst().get();
      List<Edge> others =
          neighbors.stream()
              .filter(e -> !e.getTarget().equals(target))
              .collect(Collectors.toList());
      List<Edge> edges = Lists.newArrayList();
      edges.add(edge);
      edges.addAll(others);
      neighbors = edges;
    }
  }

  public void addWeightedEdge(final Node destination, final int weight) {
    Edge edge = new Edge(destination, weight);
    neighbors.add(edge);
  }

  public int getWeight(final Node target) {
    Optional<Edge> edge =
        neighbors.stream()
            .filter(neighbor -> neighbor.getTarget().getName().equals(target.getName()))
            .findFirst();

    if (edge.isPresent()) {
      return edge.get().getWeight();
    }

    throw new NoSuchElementException("NO SUCH ROUTE");
  }

  @Override
  public String toString() {
    return name;
  }
}
