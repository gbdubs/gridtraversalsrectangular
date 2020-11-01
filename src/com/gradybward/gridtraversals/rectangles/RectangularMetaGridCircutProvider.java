package com.gradybward.gridtraversals.rectangles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RectangularMetaGridCircutProvider {

  public static Optional<RectangularGridCircut> findPathThroughMetaGrid(int[] widths,
      int[] heights) {
    ArrayList<GridCell> cells = new ArrayList<>();
    int x = 0;
    for (int w = 0; w < widths.length; w++) {
      int y = 0;
      for (int h = 0; h < heights.length; h++) {
        for (Corner in : Corner.values()) {
          for (Corner out : Corner.values()) {
            if (in != out) {
              GridCell gc = new GridCell(new Point(x, y), w, h, widths[w], heights[h], in, out);
              if (gc.isSatisfiable()) {
                cells.add(gc);
              }
            }
          }
        }
        y += heights[h];
      }
      x += widths[w];
    }
    int n = widths.length * heights.length;
    Optional<List<GridCell>> gridCells = FindCycleOfExactLength.findCycleOfExactLength(n, cells,
        (a, b) -> a.isAdjacentTo(b), (a, b) -> a.isExcludedWith(b));
    if (!gridCells.isPresent()) {
      return Optional.empty();
    }
    List<Point> result = new ArrayList<>();
    for (GridCell gc : gridCells.get()) {
      RectangularGridTraversalProvider provider = Providers
          .getRandomProviderForSituation(gc.width, gc.height, gc.in, gc.out).get();
      RectangularGridTraversal traversal = provider.provideWithTopLeftAtPoint(gc.topLeft, gc.width,
          gc.height, gc.in, gc.out);
      result.addAll(traversal.points);
    }
    return Optional.of(new RectangularGridCircut(result));
  }
}
