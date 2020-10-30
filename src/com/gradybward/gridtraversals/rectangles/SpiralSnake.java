package com.gradybward.gridtraversals.rectangles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class SpiralSnake extends RectangularGridTraversalProvider {

  private static List<Point> spiralFromTopLeftToBottomLeft(int width, int height) {
    List<Point> result = new ArrayList<>();
    List<Point> outer = createSingleStrandSpiral(width, height);
    List<Point> inner = Point.reverse(
        Point.translateFirstPointTo(createSingleStrandSpiral(width - 2, height - 2), 1, 1));
    List<Point> finalColumn = getFinalColumn(height);
    Set<Point> alreadyTaken = new HashSet<>();
    alreadyTaken.addAll(outer);
    alreadyTaken.addAll(inner);
    alreadyTaken.addAll(finalColumn);
    List<Point> connector = getCenterConnector(width, height, outer.get(outer.size() - 1),
        alreadyTaken);
    result.addAll(outer);
    result.addAll(connector);
    result.addAll(inner);
    result.addAll(finalColumn);
    return result;
  }

  private static List<Point> getCenterConnector(int width, int height, Point orderByClosestTo,
      Set<Point> taken) {
    List<Point> result = new ArrayList<>();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Point p = new Point(i, j);
        if (!taken.contains(p)) {
          result.add(p);
        }
      }
    }
    Collections.sort(result, (a, b) -> Integer.compare(a.manhattanDistance(orderByClosestTo),
        b.manhattanDistance(orderByClosestTo)));
    return result;
  }

  private static List<Point> getFinalColumn(int height) {
    List<Point> result = new ArrayList<>();
    for (int i = 1; i < height; i++) {
      result.add(new Point(0, i));
    }
    return result;
  }

  private static List<Point> createSingleStrandSpiral(int width, int height) {
    List<Point> result = new ArrayList<>();
    int horizontalMagnitude = width - 1;
    int verticalMagnitude = height - 1;
    int dx = 1;
    int dy = 0;
    int x = 0;
    int y = 0;
    while (horizontalMagnitude > 0 && verticalMagnitude > 0) {
      if (dx != 0) {
        for (int i = 0; i <= horizontalMagnitude; i++) {
          result.add(new Point(x + i * dx, y));
        }
      } else {
        for (int i = 0; i <= verticalMagnitude; i++) {
          result.add(new Point(x, y + i * dy));
        }
      }
      horizontalMagnitude -= 1;
      verticalMagnitude -= 1;
      x = x + horizontalMagnitude * dx;
      y = y + verticalMagnitude * dy;
      if (dx == 1) {
        dx = 0;
        dy = 1;
        y += 1;
        x += 1;
      } else if (dy == 1) {
        dx = -1;
        dy = 0;
        y += 1;
        x -= 1;
      } else if (dx == -1) {
        dx = 0;
        dy = -1;
        y -= 1;
        x -= 1;
      } else {
        dx = 1;
        dy = 0;
        y -= 1;
        x += 1;
      }
    }
    return result;
  }

  private static boolean canDoDimensions(int width, int height) {
    if (height < 4 || width < 4) {
      return false;
    }
    width = width - 1;
    if (width < height) {
      return width % 2 == 0;
    }
    return height % 2 == 0;
  }

  @Override
  protected boolean canProvideWithDimensions(int width, int height, Corner from, Corner to) {
    if (!from.isAdjacentTo(to)) {
      return false;
    }
    if (from.sameXAs(to)) {
      return canDoDimensions(width, height);
    }
    return canDoDimensions(height, width);
  }

  @Override
  protected RectangularGridTraversal provideStartingAtOrigin(int width, int height, Corner from,
      Corner to) {
    if (from == Corner.TOP_LEFT && to == Corner.BOTTOM_LEFT) {
      return new RectangularGridTraversal(spiralFromTopLeftToBottomLeft(width, height));
    }
    if (from == Corner.BOTTOM_LEFT && to == Corner.TOP_LEFT) {
      return new RectangularGridTraversal(spiralFromTopLeftToBottomLeft(width, height))
          .flipYValues();
    }
    if (from == Corner.TOP_RIGHT && to == Corner.BOTTOM_RIGHT) {
      return new RectangularGridTraversal(spiralFromTopLeftToBottomLeft(width, height))
          .flipXValues();
    }
    if (from == Corner.BOTTOM_RIGHT && to == Corner.TOP_RIGHT) {
      return new RectangularGridTraversal(spiralFromTopLeftToBottomLeft(width, height))
          .flipYValues().flipXValues();
    }
    if (from == Corner.TOP_LEFT && to == Corner.TOP_RIGHT) {
      return new RectangularGridTraversal(spiralFromTopLeftToBottomLeft(height, width))
          .swapXYValues();
    }
    if (from == Corner.TOP_RIGHT && to == Corner.TOP_LEFT) {
      return new RectangularGridTraversal(spiralFromTopLeftToBottomLeft(height, width))
          .swapXYValues().flipXValues();
    }
    if (from == Corner.BOTTOM_LEFT && to == Corner.BOTTOM_RIGHT) {
      return new RectangularGridTraversal(spiralFromTopLeftToBottomLeft(height, width))
          .swapXYValues().flipYValues();
    }
    if (from == Corner.BOTTOM_RIGHT && to == Corner.BOTTOM_LEFT) {
      return new RectangularGridTraversal(spiralFromTopLeftToBottomLeft(height, width))
          .swapXYValues().flipXValues().flipYValues();
    }
    throw new IllegalArgumentException("The corners provided were not adjacent.");
  }
}
