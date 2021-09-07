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

  public Node(final String name) {
    this.name = name;
    this.neighbors = Lists.newArrayList();
  }

  public void addWeightedEdge(final Node destination, final int weight) {
    Edge edge = new Edge(destination, weight);
    neighbors.add(edge);
  }

  public int getWeight(final Node destination, final int override) {
    Optional<Edge> edge =
        neighbors.stream()
            .filter(neighbor -> neighbor.getTarget().getName().equals(destination.getName()))
            .findFirst();

    if (edge.isPresent()) {
      return edge.get().getWeight();
    }

    if (override == 0) {
      throw new NoSuchElementException("NO SUCH ROUTE");
    }

    return 0;
  }

  @Override
  public String toString() {
    return name;
  }
}
