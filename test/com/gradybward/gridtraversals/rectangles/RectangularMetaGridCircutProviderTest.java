package com.gradybward.gridtraversals.rectangles;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class RectangularMetaGridCircutProviderTest {

  private final int[] widths;
  private final int[] heights;
  private final boolean expectedToBePresent;

  public RectangularMetaGridCircutProviderTest(String testName, int[] widths, int[] heights,
      boolean expectedToBePresent) {
    this.widths = widths;
    this.heights = heights;
    this.expectedToBePresent = expectedToBePresent;
  }

  @Test
  public void passesConstructionCriteria() {
    Optional<RectangularGridCircut> circut = RectangularMetaGridCircutProvider
        .findPathThroughMetaGrid(widths, heights);
    if (expectedToBePresent) {
      assertTrue(circut.isPresent());
    }
  }

  @Parameterized.Parameters(name = "{0} - {3}")
  public static List<Object[]> testCases() {
    int[][] widths = new int[][] { { 2, 2 }, { 2, 3 }, { 2, 4 }, { 3, 4 }, { 4, 5 }, { 3, 4, 4 },
        { 2, 2, 8, 4 }, };
    int[][] heights = new int[][] { { 2, 2 }, { 2, 3 }, { 2, 4 }, { 3, 4 }, { 3, 5 }, { 4, 5, 6 },
        { 3, 5, 2, 5 } };

    List<Object[]> result = new ArrayList<>();
    for (int[] w : widths) {
      for (int[] h : heights) {
        String desc = String.format("%sx%s", Arrays.toString(w), Arrays.toString(h));
        result.add(new Object[] { desc, w, h, false });
      }
    }
    int[][] expectedPresentWidths = new int[][] { { 2, 2 }, { 2, 4 } };
    int[][] expectedPresentHeights = new int[][] { { 2, 2 }, { 2, 3 }, { 2, 4 },
        { 4, 8, 5, 4, 8 }, };
    for (int[] w : expectedPresentWidths) {
      for (int[] h : expectedPresentHeights) {
        String desc = String.format("%sx%s", Arrays.toString(w), Arrays.toString(h));
        result.add(new Object[] { desc, w, h, true });
      }
    }
    return result;
  }
}
