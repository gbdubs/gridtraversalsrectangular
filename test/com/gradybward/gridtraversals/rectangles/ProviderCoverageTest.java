package com.gradybward.gridtraversals.rectangles;

import com.gradybward.hamiltonian.HamiltonianCycleSolver;

import static org.junit.Assert.assertFalse;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ProviderCoverageTest {

  private final int w;
  private final int h;
  private final Corner from;
  private final Corner to;

  public ProviderCoverageTest(int w, int h, Corner from, Corner to) {
    this.w = w;
    this.h = h;
    this.from = from;
    this.to = to;
  }

  @Test
  public void everyPossibleSetOfDimensionsIsCoveredByAtLeastOneProvider() {
    if (Providers.getProvidersForSituation(w, h, from, to).isEmpty()) {
      // Asserts that we don't have providers only for situations that are impossible to satisfy.
      assertFalse(isPossibleToCreateAGridWithConstraints(w, h, from, to));
    }
  }

  // Determines whether any path can satisfy these constraints by doing a Hamiltonian cycle analysis
  // and creating a synthetic point to link the first and last points.
  private static boolean isPossibleToCreateAGridWithConstraints(int w, int h, Corner from,
      Corner to) {
    List<Point2D.Double> points = new ArrayList<>();
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        points.add(new Point2D.Double(i, j));
      }
    }
    Point2D.Double fromPoint = getCorner(from, w, h);
    Point2D.Double toPoint = getCorner(to, w, h);

    Point2D.Double globalConnectorPoint = null;
    points.add(globalConnectorPoint);
    BiPredicate<Point2D.Double, Point2D.Double> areAdjacent = (p1, p2) -> {
      if (p1 == globalConnectorPoint) {
        return p2 != null && (p2.equals(fromPoint) || p2.equals(toPoint));
      } else if (p2 == globalConnectorPoint) {
        return p1 != null && (p1.equals(fromPoint) || p1.equals(toPoint));
      }
      return p1.distance(p2) < 1.01;
    };
    return HamiltonianCycleSolver.DFS().findHamiltonianCycle(points, areAdjacent).isPresent();
  }

  private static Point2D.Double getCorner(Corner corner, int w, int h) {
    switch (corner) {
      case TOP_LEFT:
        return new Point2D.Double(0, 0);
      case TOP_RIGHT:
        return new Point2D.Double(w - 1, 0);
      case BOTTOM_LEFT:
        return new Point2D.Double(0, h - 1);
      case BOTTOM_RIGHT:
        return new Point2D.Double(w - 1, h - 1);
    }
    throw new IllegalStateException("ONly four corners duh");
  }

  @Parameterized.Parameters(name = "({0}x{1}) {2} to {3}")
  public static List<Object[]> testCases() {
    // Increase these numbers at your peril.
    int minW = 2;
    int maxW = 5;
    int minH = 2;
    int maxH = 7;

    List<Object[]> result = new ArrayList<>();
    for (int w = minW; w <= maxW; w++) {
      for (int h = minH; h <= maxH; h++) {
        for (Corner in : Corner.values()) {
          for (Corner out : Corner.values()) {
            if (in != out) {
              result.add(new Object[] { w, h, in, out });
            }
          }
        }
      }
    }
    return result;
  }
}
