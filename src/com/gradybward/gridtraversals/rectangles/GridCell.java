package com.gradybward.gridtraversals.rectangles;

final class GridCell {

  final Point topLeft;
  final int xIndex;
  final int yIndex;
  final int width;
  final int height;
  final Corner in;
  final Corner out;

  public GridCell(Point topLeft, int xIndex, int yIndex, int w, int h, Corner in, Corner out) {
    this.topLeft = topLeft;
    this.xIndex = xIndex;
    this.yIndex = yIndex;
    this.width = w;
    this.height = h;
    this.in = in;
    this.out = out;
  }

  // NOTE: This is NOT a commutative function. This simultaneously solves traversal and in/out.
  boolean isAdjacentTo(GridCell other) {
    if (Math.abs(xIndex - other.xIndex) + Math.abs(yIndex - other.yIndex) != 1) {
      return false;
    }
    Direction d = Direction.fromDiff(xIndex, yIndex, other.xIndex, other.yIndex);
    return getNextInFromThisOut(d) == other.in;
  }

  boolean isExcludedWith(GridCell other) {
    return xIndex == other.xIndex && yIndex == other.yIndex;
  }

  Corner getNextInFromThisOut(Direction direction) {
    return direction.getAdjacentCorner(out);
  }

  boolean isSatisfiable() {
    return !Providers.getProvidersForSituation(width, height, in, out).isEmpty();
  }

  @Override
  public String toString() {
    return String.format("%s -> (%s, %s) [%sx%s] -> %s", in, xIndex, yIndex, width, height, out);
  }
}
