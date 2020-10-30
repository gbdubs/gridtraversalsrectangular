package com.gradybward.gridtraversals.rectangles;

import java.util.ArrayList;
import java.util.List;

final class HorizontalSnake extends RectangularGridTraversalProvider {

  static List<Point> constructPoints(int width, int height) {
    List<Point> result = new ArrayList<>();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int actualX = y % 2 == 0 ? x : width - x - 1;
        result.add(new Point(actualX, y));
      }
    }
    return result;
  }

  @Override
  public boolean canProvideWithDimensions(int w, int h, Corner from, Corner to) {
    if (from.sameYAs(to)) {
      return false;
    }
    if (h % 2 == 0) {
      return from.sameXAs(to);
    }
    return !from.sameXAs(to);
  }

  @Override
  public RectangularGridTraversal provideStartingAtOrigin(int w, int h, Corner from, Corner to) {
    RectangularGridTraversal snake = new RectangularGridTraversal(constructPoints(w, h));
    switch (from) {
      case TOP_LEFT:
        return snake;
      case TOP_RIGHT:
        return snake.flipXValues();
      case BOTTOM_RIGHT:
        return snake.flipXValues().flipYValues();
      case BOTTOM_LEFT:
        return snake.flipYValues();
    }
    throw new IllegalStateException("Only 4 corner caes are legal!");
  }
}
