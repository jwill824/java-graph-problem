package com.example.dao;

import static org.junit.Assert.assertEquals;

import com.example.builder.GraphBuilder;
import com.google.common.collect.Lists;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Before;
import org.junit.Test;

public class GraphDaoTest {
  private GraphDao dao;

  @Before
  public void setup() {
    Graph<String, DefaultWeightedEdge> graph =
        new GraphBuilder("AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7").build();
    dao = new GraphDao(graph);
  }

  @Test
  public void findTotalDistanceOfPath_Given_ABC_Expect_9() {
    assertEquals("9", dao.findTotalDistanceOfPath(Lists.newArrayList("A", "B", "C")));
  }

  @Test
  public void findTotalDistanceOfPath_Given_AD_Expect_5() {
    assertEquals("5", dao.findTotalDistanceOfPath(Lists.newArrayList("A", "D")));
  }

  @Test
  public void findTotalDistanceOfPath_Given_ADC_Expect_13() {
    assertEquals("13", dao.findTotalDistanceOfPath(Lists.newArrayList("A", "D", "C")));
  }

  @Test
  public void findTotalDistanceOfPath_Given_AEBCD_Expect_22() {
    assertEquals("22", dao.findTotalDistanceOfPath(Lists.newArrayList("A", "E", "B", "C", "D")));
  }

  @Test
  public void findTotalDistanceOfPath_Given_AED_Expect_NoSuchRoute() {
    assertEquals("NO SUCH ROUTE", dao.findTotalDistanceOfPath(Lists.newArrayList("A", "E", "D")));
  }

  @Test
  public void findNumberOfPathsBetweenTwoNodesGivenStops_Given_CC32_Expect_2() {
    assertEquals(2, dao.findNumberOfPathsBetweenTwoNodesGivenStops("C", "C", 3, 2).intValue());
  }

  @Test
  public void findNumberOfPathsBetweenTwoNodesGivenStops_Given_AC40_Expect_3() {
    assertEquals(3, dao.findNumberOfPathsBetweenTwoNodesGivenStops("A", "C", 4, 0).intValue());
  }

  @Test
  public void findShortestPathBetweenTwoNodes_Given_CC_Expect_9() {
    assertEquals(9, dao.findShortestDistanceBetweenTwoNodes("C", "C").intValue());
  }

  @Test
  public void findShortestPathBetweenTwoNodes_Given_AC_Expect_9() {
    assertEquals(9, dao.findShortestDistanceBetweenTwoNodes("A", "C").intValue());
  }

  @Test
  public void findNumberOfPathsBetweenTwoNodesGivenDistance_Given_CC301_Expect_7() {
    assertEquals(7, dao.findNumberOfPathsBetweenTwoNodesGivenDistance("C", "C", 30, 1).intValue());
  }
}