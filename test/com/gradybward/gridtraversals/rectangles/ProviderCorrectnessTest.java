package com.gradybward.gridtraversals.rectangles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ProviderCorrectnessTest {

  private final RectangularGridTraversalProvider provider;
  private final int w;
  private final int h;
  private final Corner from;
  private final Corner to;

  public ProviderCorrectnessTest(String testName, RectangularGridTraversalProvider provider, int w,
      int h, Corner from, Corner to) {
    this.provider = provider;
    this.w = w;
    this.h = h;
    this.from = from;
    this.to = to;
  }

  @Test
  public void passesConstructionCriteria() {
    provider.provideStartingAtPoint(new Point(0, 0), w, h, from, to);
  }

  @Parameterized.Parameters(name = "{0} ({2}x{3}) {4} to {5}")
  public static List<Object[]> testCases() {
    Map<RectangularGridTraversalProvider, String> providers = new HashMap<>();
    providers.put(new CornerSnake(), "CornerSnake");
    providers.put(new HorizontalSnake(), "HorizontalSnake");
    providers.put(new SpiralSnake(), "SpiralSnake");
    providers.put(new VerticalSnake(), "VerticalSnake");
    providers.put(new CombSnake(), "CombSnake");
    int minW = 2;
    int maxW = 10;
    int minH = 2;
    int maxH = 10;

    List<Object[]> result = new ArrayList<>();
    for (RectangularGridTraversalProvider provider : providers.keySet()) {
      for (int w = minW; w <= maxW; w++) {
        for (int h = minH; h <= maxH; h++) {
          for (Corner in : Corner.values()) {
            for (Corner out : Corner.values()) {
              if (provider.canProvide(w, h, in, out)) {
                result.add(new Object[] { providers.get(provider), provider, w, h, in, out });
              }
            }
          }
        }
      }
    }
    return result;
  }
}
