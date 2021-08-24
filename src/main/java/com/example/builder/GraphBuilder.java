package com.example.builder;

import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class GraphBuilder {
  private Graph<String, DefaultWeightedEdge> graph;

  public GraphBuilder(final String graphString) {
    graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

    if (StringUtils.isNotBlank(graphString)) {
      Stream.of(graphString.split(","))
          .forEach(
              str -> {
                if (!graph.containsVertex(Character.toString(str.charAt(0)))) {
                  graph.addVertex(Character.toString(str.charAt(0)));
                }
                if (!graph.containsVertex(Character.toString(str.charAt(1)))) {
                  graph.addVertex(Character.toString(str.charAt(1)));
                }
                graph.addEdge(Character.toString(str.charAt(0)), Character.toString(str.charAt(1)));
                graph.setEdgeWeight(
                    Character.toString(str.charAt(0)),
                    Character.toString(str.charAt(1)),
                    Double.parseDouble(Character.toString(str.charAt(2))));
              });
    }
  }

  public Graph<String, DefaultWeightedEdge> build() {
    return graph;
  }
}
