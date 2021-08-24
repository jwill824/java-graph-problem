package com.example.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.example.validation.GraphValidator;
import com.example.validation.NodeValidator;
import com.example.validation.PathValidator;
import lombok.Getter;

@Getter
@Parameters(
    commandNames = {"distance"},
    commandDescription = "Used for calculating distance in graph")
public class DistanceCommand {
  @Parameter(
      names = {"--path"},
      description = "The path",
      validateValueWith = PathValidator.class)
  private String path;

  @Parameter(
      names = {"--start"},
      description = "The start node",
      validateValueWith = NodeValidator.class)
  private String startNode;

  @Parameter(
      names = {"--end"},
      description = "The end node",
      validateValueWith = NodeValidator.class)
  private String endNode;

  @Parameter(
      names = {"--graph"},
      description = "The graph",
      validateValueWith = GraphValidator.class)
  private String graph;

  @Parameter(names = "--help", help = true)
  private boolean help;
}
