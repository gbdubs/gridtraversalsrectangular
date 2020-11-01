package com.gradybward.gridtraversals.rectangles;

enum Direction {
  TOP,
  RIGHT,
  BOTTOM,
  LEFT;

  Corner getAdjacentCorner(Corner adjacentTo) {
    switch (adjacentTo) {
      case TOP_LEFT:
        switch (this) {
          case TOP:
            return Corner.BOTTOM_LEFT;
          case LEFT:
            return Corner.TOP_RIGHT;
          default:
            return null;
        }
      case TOP_RIGHT:
        switch (this) {
          case TOP:
            return Corner.BOTTOM_RIGHT;
          case RIGHT:
            return Corner.TOP_LEFT;
          default:
            return null;
        }
      case BOTTOM_RIGHT:
        switch (this) {
          case BOTTOM:
            return Corner.TOP_RIGHT;
          case RIGHT:
            return Corner.BOTTOM_LEFT;
          default:
            return null;
        }
      case BOTTOM_LEFT:
        switch (this) {
          case BOTTOM:
            return Corner.TOP_LEFT;
          case LEFT:
            return Corner.BOTTOM_RIGHT;
          default:
            return null;
        }
      default:
        return null;
    }
  }

  static Direction fromDiff(int x1, int y1, int x2, int y2) {
    if (x1 < x2) {
      return RIGHT;
    }
    if (x1 > x2) {
      return LEFT;
    }
    if (y1 < y2) {
      return BOTTOM;
    }
    if (y1 > y2) {
      return TOP;
    }
    throw new RuntimeException("Coordinates should not be equal.");
  }
}
