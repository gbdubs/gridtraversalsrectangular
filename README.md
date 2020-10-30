# Rectangular Grid Traversals

This package gives a collection of different methodologies for traversing a rectangular unit-grid from one corner to another.

Four methodologies are currently supported:

- Horizontal Snake
- Corner Snake
- Vertical Snake
- Spiral Snake

Each of these have quirks for when they can be used (based on the height and width and corners to be traversed).
This complexity is abstracting by giving an interface `Providers` that allows you to see all traversal options
given your current set of constraints. The canonical usage is

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

## Guarentees

All traversals will have the following properties:

- They will lie on the integer grid.
- They will start at the point origin.
- They will complete a complete tour of a rectangle of the given width and height [0, w), [0, h).
- The traversal will have no duplicate points.
- The precessor and successor of every point will be a cartesian distance of 1 away from it.
- The traversal will start and end at the designated corners.

## Tests

This is validated using parameterized tests that check each of these guarentees up to 10x10 grids.
Note that we only expect these properties to hold for combinations of inputs that are supported (returned by `getProvidersForSituation`)

## Contrib

This is part of a few algorithmic art projects I've been working on. Please feel free to recommend other
programmatic means of traversing a rectangular unit grid, I'd be happy to implement them!
