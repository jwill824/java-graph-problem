package com.example.builder;

import com.example.model.Node;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public class GraphBuilder {
  private Map<String, Node> graph = new HashMap<>();

  public GraphBuilder(final String graphString) {
    if (StringUtils.isNotBlank(graphString)) {
      List<Node> vertices =
          Stream.of(graphString.split(","))
              .map(letter -> letter.split(""))
              .flatMap(Arrays::stream)
              .distinct()
              .filter(edge -> edge.matches("[A-Z]+"))
              .map(Node::new)
              .collect(Collectors.toList());

      Stream.of(graphString.split(","))
          .forEach(
              edge -> {
                vertices.forEach(
                    vertex -> {
                      if (vertex.getName().equals(Character.toString(edge.charAt(0)))) {
                        Optional<Node> dest =
                            vertices.stream()
                                .filter(v -> v.getName().equals(Character.toString(edge.charAt(1))))
                                .findFirst();
                        if (!dest.isPresent()) {
                          dest = Optional.of(new Node(Character.toString(edge.charAt(1))));
                        }
                        vertex.addWeightedEdge(
                            dest.get(), Integer.parseInt(Character.toString(edge.charAt(2))));
                      }
                    });
              });

      vertices.stream().forEach(vertex -> graph.put(vertex.getName(), vertex));
    }
  }

  public Map<String, Node> build() {
    return graph;
  }
}
