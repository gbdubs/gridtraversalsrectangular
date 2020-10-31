package com.gradybward.gridtraversals.rectangles;

import java.util.ArrayList;
import java.util.List;

final class CombSnake extends RectangularGridTraversalProvider {

  @Override
  protected boolean canProvideWithDimensions(int w, int h, Corner from, Corner to) {
    if (w <= 2 || h <= 2) {
      return false;
    }
    Corner synthetic = getSyntheticCorner(from, to);
    if (from.sameXAs(synthetic)) {
      return new HorizontalSnake().canProvide(w - 1, h, synthetic, to);
    }
    return new VerticalSnake().canProvide(w, h - 1, synthetic, to);
  }

  @Override
  protected RectangularGridTraversal provideStartingAtOrigin(int w, int h, Corner from, Corner to) {
    return new RectangularGridTraversal(getPoints(w, h, from, to));
  }

  private static List<Point> getPoints(int w, int h, Corner from, Corner to) {
    Corner synthetic = getSyntheticCorner(from, to);
    List<Point> result = new ArrayList<>();
    if (from.sameXAs(synthetic)) {
      List<Point> sideLine = new ArrayList<>();
      int x;
      int originX;
      if (from.sameXAs(Corner.TOP_LEFT)) {
        x = 0;
        originX = 1;
      } else {
        x = w - 1;
        originX = w - 2;
      }

      for (int i = 0; i < h; i++) {
        sideLine.add(new Point(x, i));
      }
      int originY;
      if (from.sameYAs(Corner.BOTTOM_RIGHT)) {
        sideLine = Point.reverse(sideLine);
        originY = 0;
      } else {
        originY = h - 1;
      }
      result.addAll(sideLine);
      List<Point> snake = new HorizontalSnake()
          .provideStartingAtPoint(new Point(originX, originY), w - 1, h, synthetic, to).getAll();
      result.addAll(snake);
    } else if (from.sameYAs(synthetic)) {
      List<Point> topLine = new ArrayList<>();
      int y;
      int originY;
      if (from.sameYAs(Corner.TOP_LEFT)) {
        y = 0;
        originY = 1;
      } else {
        y = h - 1;
        originY = h - 2;
      }
      for (int i = 0; i < w; i++) {
        topLine.add(new Point(i, y));
      }
      int originX;
      if (from.sameXAs(Corner.BOTTOM_RIGHT)) {
        topLine = Point.reverse(topLine);
        originX = 0;
      } else {
        originX = w - 1;
      }
      result.addAll(topLine);
      result.addAll(new VerticalSnake()
          .provideStartingAtPoint(new Point(originX, originY), w, h - 1, synthetic, to).getAll());
    }
    return result;
  }

  private static Corner getSyntheticCorner(Corner from, Corner to) {
    for (Corner corner : Corner.values()) {
      if (corner != from && corner != to && corner.isAdjacentTo(from)) {
        return corner;
      }
    }
    throw new IllegalStateException("Should have found a corner!");
  }

}
