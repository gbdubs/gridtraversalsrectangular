package com.gradybward.gridtraversals.rectangles;

public enum Corner {
  TOP_LEFT(0, 0),
  TOP_RIGHT(1, 0),
  BOTTOM_RIGHT(1, 1),
  BOTTOM_LEFT(0, 1);

  private final int x;
  private final int y;

  private Corner(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean sameXAs(Corner other) {
    return this.x == other.x;
  }

  public boolean sameYAs(Corner other) {
    return this.y == other.y;
  }

  public boolean isAdjacentTo(Corner other) {
    return (sameXAs(other) || sameYAs(other)) && other != this;
  }

  public static Corner getFromCoords(Point topLeft, Point p) {
    if (topLeft.equals(p)) {
      return TOP_LEFT;
    }
    if (topLeft.x == p.x) {
      return BOTTOM_LEFT;
    }
    if (topLeft.y == p.y) {
      return TOP_RIGHT;
    }
    return BOTTOM_RIGHT;
  }
}
