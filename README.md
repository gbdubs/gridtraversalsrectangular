# Rectangular Grid Traversals

This package gives a collection of different methodologies for traversing a rectangular unit-grid.

## Corner to Corner Traversal

Four methodologies are currently supported for corner to corner traversal:

- Horizontal Snake (snakes up and down while traveling horizontally)
- Vertical Snake (snakes left to right while traveling vertically)
- Corner Snake (snakes horizontally and vertically, changing orientation at x == y)
- Spiral Snake (constructs a spiral in + spiral out)
- Comb Snake (A straignt line plus a horizontal or vertical snake in the opposite direction)

Each of these have quirks for when they can be used (based on the height and width and corners to be traversed).
This complexity is abstracting by giving an interface `Providers` that allows you to see all traversal options
given your current set of constraints. The canonical usage is

### Usage

```
import com.gradybward.gridtraversals.rectangles.Corner;
import com.gradybward.gridtraversals.rectangles.Providers;

int width = 10;
int height = 31;
Corner startingAt = Corner.TOP_RIGHT;
Corner goingTo = Corner.BOTTOM_LEFT;
Point2D.Double origin = new Point2D.Double(10, 20);

List<Point2D.Double> traversal = Providers.getProvidersForSituation(width, height, startingAt, goingTo)
	.get(0).provideStartingAtPoint(origin, width, height, startingAt, goingTo).get();
```

### Guarentees

All traversals will have the following properties:

- They will lie on the integer grid.
- They will start at the point origin.
- They will complete a complete tour of a rectangle of the given width and height \[0, w), \[0, h).
- The traversal will have no duplicate points.
- The precessor and successor of every point will be a cartesian distance of 1 away from it.
- The traversal will start and end at the designated corners.


## Circut Traversal

One methodology is currently supported to traverse the full grid in a circut (the same end and start point).

- RectangularMetaGrid (divides the grid into components that can be circuted by corner-to-corner traversal, then finds an arragement of corner to corner traversals that will work)

### Usage

```
import com.gradybward.gridtraversals.rectangles.RectangularMetaGrid;
import com.gradybward.gridtraversals.rectangles.Providers;

int[] widths = new int[]{8, 5, 3}; // Total width will be 16
int[] heights = new int[]{5, 4, 2, 2}; // Total height will be 13

// RectangularGridCircut is a tour of the full 16 x 13 grid, with "cells" of [[8,5], [8, 4], [8, 2] ... etc]
Optional<RectangularGridCircut> circut = RectangularMetaGridCircutProvider.findPathThroughMetaGrid(widths, heights);

// Some combinations of values are impossible to fulfill: (ex: {3, 4}, {3, 4}), so this may not return a result.
// Larger numbers of cells are more likely to return a successful result.
List<Point2D.Double> points = circut.get().get();
```

### Guarentees

All circuts will have the following properties:

- They will have all guarentees of traversals.
- Their first and last elements will be adjacent.

## Tests

These properties are validated by parameterized tests that (though not exhaustive) fully excercise all code paths.

## Contrib

This is part of a few algorithmic art projects I've been working on. Please feel free to recommend other
programmatic means of traversing a rectangular unit grid, I'd be happy to implement them!
