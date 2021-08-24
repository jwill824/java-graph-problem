# Graph Calculator (of sorts)

A Java/SpringBoot Command Line application that interfaces with JGraphT to get certain graph calculations.

Demonstrates:

* Java/Maven
* SpringBoot 2.x (**full disclosure: this does not need to be a SpringBoot app at all, but is for demonstration**)
* JUnit 4.x
* [JCommander](https://jcommander.org/)
* [JGraphT](https://jgrapht.org/)

## Overview

In the `application.properties` file, there's a property that is assigned a graph that is:

a) directional

b) cyclical

c) weighted

Example:
```
graph=AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7
```

The above format is assumed. Meaning it shouldn't be altered. There's a validator that checks the format.

This is pulled into `GraphConfiguration.java` class and creates a bean of our Graph data structure (where `GraphConverter.java` transforms the graph string into the data structure we need) that we can pull directly into our `GraphDao.java` class aka our "pseudo DAO" (Data Access Object).

The pseudo DAO is our interface to the graph library. This contains four operations that are currently in use:

1) *findTotalDistanceOfPath()* - simply calculates weight of edges as it travels along nodes in a path
2) *findShortestDistanceBetweenTwoNodes()* - utilizes JGraphT's `DijkstraShortestPath` algorithm to calculate the shortest total distance between two nodes
3) *findNumberOfPathsBetweenTwoNodesGivenDistance()* - utilizes JGraphT's `AllDirectedPaths` class to populate a `GraphPath` structure that allows us to do Java Stream filters on it
4) *findNumberOfPathsBetweenTwoNodesGivenStops()* - - utilizes JGraphT's `AllDirectedPaths` class to populate a `GraphPath` structure that allows us to do Java Stream filters on it

Now we can move on to the command line app, located in `ConsoleApplication.java`. Here we're using JCommander to help us parse command line arguments. It's currently broken up between two commands:

1) *DistanceCommand* - command for calculating distance
2) *PathsCommand* - command for calculating number of nodes traversed

## Usage

To calculate the total distance of a path:

> Note: `--path` needs to be in 'A,B,C' format

```bash
$ mvn clean install --quiet spring-boot:run -Dspring-boot.run.arguments="distance --path A,B,C"
The total distance of A,B,C is 9
```

To find the shortest path between two nodes:

> Note: `--start` and `--end` need to be a valid node string

```bash
$ mvn clean install --quiet spring-boot:run -Dspring-boot.run.arguments="distance --start A --end C"
The shortest distance between A and C is 9
```

To find the number of paths between two nodes, given a constraint of distance:

> Note:
> `--start` and `--end` need to be a valid node string
> `--distance` needs to be a positive integer
> `--comparator` needs to be within [0, 1, 2]

```bash
$ mvn clean install --quiet spring-boot:run -Dspring-boot.run.arguments="paths --start C --end C --distance 30 --comparator 1"
The number of paths between C and C with total distance less than 30: 7
```

To find the number of paths between two nodes, given a constraint of number of stops:

> Note:
> `--start` and `--end` need to be a valid node string
> `--stops` needs to be a positive integer
> `--comparator` needs to be within [0, 1, 2]

```bash
$ mvn clean install --quiet spring-boot:run -Dspring-boot.run.arguments="paths --start A --end C --stops 4 --comparator 0"
The number of paths between A and C with stops equal to 4: 3
```

Or you can compile and run the app using the java cli after you build:

```bash
mvn clean install
```

```bash
$ java -cp target/graph-problem-1.0.0-SNAPSHOT.jar com.example.ConsoleApplication distance --path A,B,C
The total distance of A,B,C is 9
```

## Testing

There are currently tests located in `src/test/java/example/dao/GraphDaoTest.java` that test against example scenarios.

Debugging was done through VS Code.