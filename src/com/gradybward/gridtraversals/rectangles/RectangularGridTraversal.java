package com.gradybward.gridtraversals.rectangles;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class RectangularGridTraversal {

  private final List<Point> points;
  private final int width;
  private final int height;

  RectangularGridTraversal(List<Point> points) {
    this.points = points;
    int minX = points.stream().mapToInt(p -> p.x).min().getAsInt();
    int maxX = points.stream().mapToInt(p -> p.x).max().getAsInt();
    width = maxX - minX + 1;
    int minY = points.stream().mapToInt(p -> p.y).min().getAsInt();
    int maxY = points.stream().mapToInt(p -> p.y).max().getAsInt();
    height = maxY - minY + 1;
    assertComplete();
    assertAdjacent();
    assertDistinct();
  }
  
  public List<Point2D.Double> get() {
    return Point.toDouble(points);
  }

  Point getFirst() {
    return points.get(0);
  }

  Point getLast() {
    return points.get(points.size() - 1);
  }

  Point getTopLeftCorner() {
    return new Point(points.stream().mapToInt(p -> p.x).min().getAsInt(),
        points.stream().mapToInt(p -> p.y).min().getAsInt());
  }

  Point getBottomRightCorner() {
    return new Point(points.stream().mapToInt(p -> p.x).max().getAsInt(),
        points.stream().mapToInt(p -> p.y).max().getAsInt());
  }

  List<Point> getAll() {
    return new ArrayList<>(points);
  }

  private void assertComplete() {
    int expected = width * height;
    if (points.size() != expected) {
      throw new IllegalArgumentException(
          String.format("Exected %s (%s x %s) elements, but found %s:\n%s", expected, width, height,
              points.size(), points.toString().replaceAll(",", ",\n")));
    }
  }

  private void assertAdjacent() {
    for (int i = 0; i < points.size() - 1; i++) {
      Point p1 = points.get(i);
      Point p2 = points.get(i + 1);
      int dx = (int) Math.abs(p1.x - p2.x);
      int dy = (int) Math.abs(p1.y - p2.y);
      if (dx + dy > 1) {
        throw new IllegalArgumentException(
            String.format("Points are not adjacent: %s, %s", p1, p2));
      }
    }
  }

  private void assertDistinct() {
    Set<Point> deduped = new HashSet<>(points);
    if (points.size() != deduped.size()) {
      throw new IllegalArgumentException("Point list contains dupes: " + points.toString());
    }
  }

  RectangularGridTraversal translateFirstPointTo(int x, int y) {
    return new RectangularGridTraversal(Point.translateFirstPointTo(points, x, y));
  }

  RectangularGridTraversal reverse() {
    return new RectangularGridTraversal(Point.reverse(points));
  }

  RectangularGridTraversal flipXValues() {
    return new RectangularGridTraversal(Point.flipXValues(points));
  }

  RectangularGridTraversal flipYValues() {
    return new RectangularGridTraversal(Point.flipYValues(points));
  }

  RectangularGridTraversal swapXYValues() {
    return new RectangularGridTraversal(Point.swapXYValues(points));
  }

  @Override
  public String toString() {
    return String.format("Rectangular Traversal: Top-Left=%s Bottom-Right=%s First=%s Last=%s",
        getTopLeftCorner(), getBottomRightCorner(), getFirst(), getLast());
  }
}
