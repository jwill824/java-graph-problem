package com.example;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.internal.Lists;
import com.example.builder.GraphBuilder;
import com.example.commands.DistanceCommand;
import com.example.commands.PathsCommand;
import com.example.dao.GraphDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {
  private GraphDao dao;

  public ConsoleApplication(final GraphDao dao) {
    this.dao = dao;
  }

  public static void main(String[] args) {
    SpringApplication.run(ConsoleApplication.class, args);
  }

  @Override
  public void run(String... args) {
    DistanceCommand distanceCommand = new DistanceCommand();
    PathsCommand pathsCommand = new PathsCommand();
    JCommander command =
        JCommander.newBuilder().addCommand(distanceCommand).addCommand(pathsCommand).build();

    String parsedCommand;

    try {
      if (args.length == 0) {
        throw new ParameterException("No args were given");
      }

      command.parse(args);
      parsedCommand = command.getParsedCommand();

      switch (parsedCommand) {
        case "distance":
          if (distanceCommand.isHelp()) {
            command.getCommands().get(parsedCommand).usage();
            break;
          }

          if (StringUtils.isNotBlank(distanceCommand.getGraph())) {
            dao = new GraphDao(new GraphBuilder(distanceCommand.getGraph()).build());
          }

          if (StringUtils.isNotBlank(distanceCommand.getPath())) {
            System.out.println(
                String.format(
                    "The total distance of %s is %s",
                    distanceCommand.getPath(),
                    dao.findTotalDistanceOfPath(
                        Lists.newArrayList(distanceCommand.getPath().split(",")))));
            break;
          }

          if (StringUtils.isNotBlank(distanceCommand.getStartNode())
              && StringUtils.isNotBlank(distanceCommand.getEndNode())
              && !distanceCommand.isHelp()) {
            System.out.println(
                String.format(
                    "The shortest distance between %s and %s is %s",
                    distanceCommand.getStartNode(),
                    distanceCommand.getEndNode(),
                    dao.findShortestDistanceBetweenTwoNodes(
                        distanceCommand.getStartNode(), distanceCommand.getEndNode())));
            break;
          }

          command.getCommands().get(parsedCommand).usage();
          break;
        case "paths":
          if (pathsCommand.isHelp()) {
            command.getCommands().get(parsedCommand).usage();
            break;
          }

          if (StringUtils.isNotBlank(pathsCommand.getGraph())) {
            dao = new GraphDao(new GraphBuilder(pathsCommand.getGraph()).build());
          }

          if (pathsCommand.getDistance() != null) {
            System.out.println(
                String.format(
                    "The number of paths between %s and %s with total distance %s %s: %s",
                    pathsCommand.getStartNode(),
                    pathsCommand.getEndNode(),
                    pathsCommand.getComparator().equals(0)
                        ? "equal to"
                        : pathsCommand.getComparator().equals(1)
                            ? "less than"
                            : "equal to or less than",
                    pathsCommand.getDistance(),
                    dao.findNumberOfPathsBetweenTwoNodesGivenDistance(
                        pathsCommand.getStartNode(),
                        pathsCommand.getEndNode(),
                        pathsCommand.getDistance(),
                        pathsCommand.getComparator())));
            break;
          }

          if (pathsCommand.getStops() != null && !pathsCommand.isHelp()) {
            System.out.println(
                String.format(
                    "The number of paths between %s and %s with stops %s %s: %s",
                    pathsCommand.getStartNode(),
                    pathsCommand.getEndNode(),
                    pathsCommand.getComparator().equals(0)
                        ? "equal to"
                        : pathsCommand.getComparator().equals(1)
                            ? "less than"
                            : "equal to or less than",
                    pathsCommand.getStops(),
                    dao.findNumberOfPathsBetweenTwoNodesGivenStops(
                        pathsCommand.getStartNode(),
                        pathsCommand.getEndNode(),
                        pathsCommand.getStops(),
                        pathsCommand.getComparator())));
            break;
          }

          command.getCommands().get(parsedCommand).usage();
          break;
      }
    } catch (ParameterException ex) {
      System.err.println(ex.getLocalizedMessage());
      parsedCommand = command.getParsedCommand();
      if (parsedCommand != null) {
        command.getCommands().get(parsedCommand).usage();
      } else {
        command.usage();
      }
    }
  }
}
