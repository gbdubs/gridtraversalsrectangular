package com.gradybward.gridtraversals.rectangles;

import java.util.List;

public class RectangularGridCircut extends RectangularGridTraversal {

  RectangularGridCircut(List<Point> points) {
    super(points);
    assertCircular();
  }

  private void assertCircular() {
    Point first = getFirst();
    Point last = getLast();
    int diff = Math.abs(first.x - last.x) + Math.abs(first.y - last.y);
    if (diff != 1) {
      throw new IllegalStateException(
          String.format("Cycle is not closed. First: %s, Last: %s.", first, last));
    }
  }
}
