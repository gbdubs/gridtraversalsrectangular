package com.gradybward.gridtraversals.rectangles;

import java.awt.geom.Point2D;

public abstract class RectangularGridTraversalProvider {

  protected abstract boolean canProvideWithDimensions(int w, int h, Corner from, Corner to);

  protected abstract RectangularGridTraversal provideStartingAtOrigin(int w, int h, Corner from,
      Corner to);

  boolean canProvide(int w, int h, Corner from, Corner to) {
    if (w <= 0 || h <= 0) {
      throw new IllegalArgumentException("Invalid Width and Height: " + w + " " + h);
    }
    if (from == to) {
      return false;
    }
    return canProvideWithDimensions(w, h, from, to);
  }

  RectangularGridTraversal provideStartingAtPoint(Point2D.Double p, int w, int h, Corner from,
      Corner to) {
    return provideStartingAtPoint(new Point((int) Math.floor(p.x), (int) Math.floor(p.y)), w, h, from, to);
  }
  
  RectangularGridTraversal provideStartingAtPoint(Point p, int w, int h, Corner from,
      Corner to) {
    if (!canProvideWithDimensions(w, h, from, to)) {
      throw new IllegalArgumentException(
          "Do not call ProvideStartingAtPoint without first calling canProvide!");
    }
    RectangularGridTraversal result = provideStartingAtOrigin(w, h, from, to)
        .translateFirstPointTo(p.x, p.y);
    if (Corner.getFromCoords(result.getTopLeftCorner(), result.getFirst()) != from) {
      throw new IllegalStateException(String.format(
          "It Looks like the rectangle didn't meet the FROM corner\n%s w=%s h=%s from=%s to=%s\n%s",
          result.getClass().getSimpleName(), w, h, from, to, result.toString()));
    }
    if (Corner.getFromCoords(result.getTopLeftCorner(), result.getLast()) != to) {
      throw new IllegalStateException(String.format(
          "It Looks like the rectangle didn't meet the TO corner\n%s w=%s h=%s from=%s to=%s\n%s",
          result.getClass().getSimpleName(), w, h, from, to, result.toString()));
    }
    return result;
  }
}
