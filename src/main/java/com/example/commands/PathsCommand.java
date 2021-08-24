package com.example.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;
import com.example.validation.ComparatorValidator;
import com.example.validation.GraphValidator;
import com.example.validation.NodeValidator;
import lombok.Getter;

@Getter
@Parameters(
    commandNames = {"paths"},
    commandDescription = "Used for calculating number of nodes traversed in graph")
public class PathsCommand {
  @Parameter(
      names = {"--start"},
      description = "The start node",
      required = true,
      validateValueWith = NodeValidator.class)
  private String startNode;

  @Parameter(
      names = {"--end"},
      description = "The end node",
      required = true,
      validateValueWith = NodeValidator.class)
  private String endNode;

  @Parameter(
      names = {"--distance"},
      description = "The distance of the path traveled between two nodes")
  private Integer distance;

  @Parameter(
      names = {"--stops"},
      description = "The number of stops taken during a path",
      validateWith = PositiveInteger.class)
  private Integer stops;

  @Parameter(
      names = {"--comparator"},
      description = "The comparator to associate with calculating the number of paths",
      required = true,
      validateValueWith = ComparatorValidator.class)
  private Integer comparator;

  @Parameter(
      names = {"--graph"},
      description = "The graph",
      validateValueWith = GraphValidator.class)
  private String graph;

  @Parameter(names = "--help", help = true)
  private boolean help;
}
