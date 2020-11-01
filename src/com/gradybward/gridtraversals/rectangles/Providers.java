package com.gradybward.gridtraversals.rectangles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Providers {
  private static final List<RectangularGridTraversalProvider> ALL = Arrays.asList(
      new VerticalSnake(), new SpiralSnake(), new HorizontalSnake(), new CornerSnake(),
      new CombSnake());

  public static List<RectangularGridTraversalProvider> getProvidersForSituation(int w, int h,
      Corner from, Corner to) {
    return ALL.stream().filter(p -> p.canProvide(w, h, from, to)).collect(Collectors.toList());
  }

  public static Optional<RectangularGridTraversalProvider> getRandomProviderForSituation(int w,
      int h, Corner from, Corner to) {
    List<RectangularGridTraversalProvider> options = getProvidersForSituation(w, h, from, to);
    if (options.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(options.get((int) Math.floor(Math.random() * options.size())));
  }
}
