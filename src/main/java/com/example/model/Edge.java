package com.example.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Edge {
  private final Node target;
  private final int weight;

  public Edge(final Node target, final int weight) {
    this.target = target;
    this.weight = weight;
  }
}
