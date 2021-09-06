package com.example.model;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node implements Comparable<Node> {

  private String name;
  private List<Edge> neighbors;
  private int minDistance = Integer.MAX_VALUE;

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

    if (!edge.isPresent()) {
      throw new NoSuchElementException("NO SUCH ROUTE");
    }

    return edge.get().getWeight();
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int compareTo(Node other) {
    return Double.compare(this.minDistance, other.minDistance);
  }
}
