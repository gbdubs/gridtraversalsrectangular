package com.gradybward.gridtraversals.rectangles;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class Point {
  public final int x;
  public final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object other) {
    return (other instanceof Point) && equals((Point) other);
  }

  public boolean equals(Point other) {
    return other.x == this.x && other.y == this.y;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(new int[] { x, y });
  }

  @Override
  public String toString() {
    return String.format("[%d %d]", x, y);
  }

  public Point2D.Double toDouble() {
    return new Point2D.Double(x, y);
  }

  public static List<Point2D.Double> toDouble(List<Point> points) {
    return points.stream().map(Point::toDouble).collect(Collectors.toList());
  }

  public static List<Point> translateFirstPointTo(List<Point> points, int x, int y) {
    int dx = x - points.get(0).x;
    int dy = y - points.get(0).y;
    return points.stream().map(p -> new Point(p.x + dx, p.y + dy)).collect(Collectors.toList());
  }

  public static List<Point> reverse(List<Point> points) {
    List<Point> result = new LinkedList<>();
    for (Point p : points) {
      result.add(0, p);
    }
    return result;
  }

  public static List<Point> flipXValues(List<Point> points) {
    int ox2 = points.get(0).x * 2;
    return points.stream().map(p -> new Point(ox2 - p.x, p.y)).collect(Collectors.toList());
  }

  public static List<Point> flipYValues(List<Point> points) {
    int oy2 = points.get(0).y * 2;
    return points.stream().map(p -> new Point(p.x, oy2 - p.y)).collect(Collectors.toList());
  }

  public static List<Point> swapXYValues(List<Point> points) {
    return points.stream().map(p -> new Point(p.y, p.x)).collect(Collectors.toList());
  }

  public int manhattanDistance(Point other) {
    return Math.abs(other.x - this.x) + Math.abs(other.y - this.y);
  }
}
