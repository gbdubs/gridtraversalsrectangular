package com.gradybward.gridtraversals.rectangles;

import java.util.ArrayList;
import java.util.List;

final class CornerSnake extends RectangularGridTraversalProvider {

  private static List<Point> snakeFromTopRightCorner(int width, int height) {
    if (width > height) {
      if (height % 2 == 1) throw new IllegalArgumentException("Combination Illegal!!");
    } else if (width < height) {
      if (width % 2 == 0) throw new IllegalArgumentException("Combination Illegal!!");
    }
    List<Point> result = new ArrayList<>();
    int h = 0;
    int w = width - 1;
    boolean goingHorizontal = true;
    boolean goingUp = false;
    while (w <= width && h <= height) {
      result.add(new Point(w, h));
      if (w == h) {
        if (w == width - 1) {
          while (++h < height) {
            result.add(new Point(w, h));
          }
          return result;
        }
        if (h == height - 1) {
          while (++w < width) {
            result.add(new Point(w, h));
          }
          return result;
        }
        goingHorizontal = !goingHorizontal;
        goingUp = true;
      }
      if (goingHorizontal) {
        if (goingUp) {
          w++;
        } else {
          w--;
        }
      } else {
        if (goingUp) {
          h++;
        } else {
          h--;
        }
      }
      if (w <= width - 1 && h == height - 1) {
        result.add(new Point(w, h));
        w++;
        goingUp = false;
      } else if (w == width - 1 && h <= height - 1) {
        result.add(new Point(w, h));
        h++;
        goingUp = false;
      }
    }
    return result;
  }

  private static List<Point> snakeFromBottomLeftCorner(int width, int height) {
    return Point.swapXYValues(snakeFromTopRightCorner(height, width));
  }

  @Override
  protected boolean canProvideWithDimensions(int width, int height, Corner from, Corner to) {
    if (!from.isAdjacentTo(to)) {
      return false;
    }
    if (width == height) {
      return true;
    }
    if (from.sameXAs(to)) {
      if (width > height) {
        return height % 2 == 0;
      }
      return width % 2 == 1;
    }
    if (width > height) {
      return height % 2 == 1;
    }
    return width % 2 == 0;
  }

  @Override
  protected RectangularGridTraversal provideStartingAtOrigin(int width, int height, Corner from,
      Corner to) {
    if (from == Corner.BOTTOM_LEFT && to == Corner.BOTTOM_RIGHT) {
      return new RectangularGridTraversal(snakeFromBottomLeftCorner(width, height));
    }
    if (from == Corner.BOTTOM_LEFT && to == Corner.TOP_LEFT) {
      return new RectangularGridTraversal(snakeFromTopRightCorner(width, height)).flipXValues()
          .flipYValues();
    }
    if (from == Corner.TOP_LEFT && to == Corner.TOP_RIGHT) {
      return new RectangularGridTraversal(snakeFromBottomLeftCorner(width, height)).flipYValues();
    }
    if (from == Corner.TOP_LEFT && to == Corner.BOTTOM_LEFT) {
      return new RectangularGridTraversal(snakeFromTopRightCorner(width, height)).flipXValues();
    }
    if (from == Corner.TOP_RIGHT && to == Corner.BOTTOM_RIGHT) {
      return new RectangularGridTraversal(snakeFromTopRightCorner(width, height));
    }
    if (from == Corner.TOP_RIGHT && to == Corner.TOP_LEFT) {
      return new RectangularGridTraversal(snakeFromBottomLeftCorner(width, height)).flipXValues()
          .flipYValues();
    }
    if (from == Corner.BOTTOM_RIGHT && to == Corner.TOP_RIGHT) {
      return new RectangularGridTraversal(snakeFromTopRightCorner(width, height)).flipYValues();
    }
    if (from == Corner.BOTTOM_RIGHT && to == Corner.BOTTOM_LEFT) {
      return new RectangularGridTraversal(snakeFromBottomLeftCorner(width, height)).flipXValues();
    }
    throw new IllegalArgumentException("Only adjacent corners are supported");
  }
}
