package com.gradybward.gridtraversals.rectangles;

import java.util.ArrayList;
import java.util.List;

final class VerticalSnake extends RectangularGridTraversalProvider {

  private static List<Point> constructPoints(int height, int width) {
    List<Point> result = new ArrayList<>();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int actualY = x % 2 == 0 ? y : height - y - 1;
        result.add(new Point(x, actualY));
      }
    }
    return result;
  }

  @Override
  protected boolean canProvideWithDimensions(int h, int w, Corner from, Corner to) {
    if (from.sameXAs(to)) {
      return false;
    }
    if (w % 2 == 0) {
      return from.sameYAs(to);
    }
    return !from.sameYAs(to);
  }

  @Override
  protected RectangularGridTraversal provideStartingAtOrigin(int h, int w, Corner from, Corner to) {
    RectangularGridTraversal snake = new RectangularGridTraversal(constructPoints(h, w));
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
    throw new IllegalStateException("Only the four corners are supported.");
  }
}
