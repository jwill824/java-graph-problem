package com.example.model;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node {

  private String name;
  private List<Edge> neighbors;
  private int prevDistance = 0;

  public Node(final String name) {
    this.name = name;
    this.neighbors = Lists.newArrayList();
  }

  public void addWeightedEdge(final Node destination, final int weight) {
    Edge edge = new Edge(destination, weight);
    neighbors.add(edge);
  }

  public int getWeight(final Node destination) {
    Optional<Edge> edge =
        neighbors.stream()
            .filter(neighbor -> neighbor.getTarget().getName().equals(destination.getName()))
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
