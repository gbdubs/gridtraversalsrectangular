package com.gradybward.gridtraversals.rectangles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

// Provides a cycle checking algorithm that looks for cycles of a specific length.
// The only additional nuance here is that the solver takes in an "exclusion set", which is phrased
// like an adjacency list, but describes the set of nodes that should not be used if this node is
// used.
final class FindCycleOfExactLength {

  static <T> Optional<List<T>> findCycleOfExactLength(int length, List<T> elements,
      BiPredicate<T, T> adjacencyFn, BiPredicate<T, T> exclusionFn) {
    Map<Integer, T> translation = new HashMap<>();
    Map<Integer, List<Integer>> adjacent = new HashMap<>();
    Map<Integer, List<Integer>> excluded = new HashMap<>();
    for (int i = 0; i < elements.size(); i++) {
      translation.put(i, elements.get(i));
      List<Integer> adj = new ArrayList<>();
      List<Integer> excl = new ArrayList<>();
      for (int j = 0; j < elements.size(); j++) {
        if (adjacencyFn.test(elements.get(i), elements.get(j))) {
          adj.add(j);
        }
        if (exclusionFn.test(elements.get(i), elements.get(j))) {
          excl.add(j);
        }
      }
      adjacent.put(i, adj);
      excluded.put(i, excl);
    }
    Optional<List<Integer>> result = null;
    for (int i = 0; i < elements.size(); i++) {
      result = new Solver(adjacent, excluded, i).getCycleOfLength(length);
      if (result.isPresent()) {
        break;
      }
    }
    if (result.isPresent()) {
      for (Integer inResult : result.get()) {
        for (Integer excludedFromResult : excluded.get(inResult)) {
          if (result.get().contains(excludedFromResult) && !inResult.equals(excludedFromResult)) {
            throw new IllegalStateException(String.format("Result contains excluded pair: %s %s",
                inResult, excludedFromResult));
          }
        }
      }
    }
    return result.map(r -> r.stream().map(translation::get).collect(Collectors.toList()));
  }

  private static final class Solver {
    private final Map<Integer, List<Integer>> adjacent;
    private final Map<Integer, List<Integer>> excluded;
    private final boolean[] seen;
    private final List<Integer> inOrder;

    private Solver(Map<Integer, List<Integer>> adjacent, Map<Integer, List<Integer>> excluded,
        Integer initial) {
      this.adjacent = adjacent;
      this.excluded = excluded;
      seen = new boolean[this.adjacent.size()];
      inOrder = new ArrayList<>();
      seen[initial] = true;
      inOrder.add(initial);
      for (int excludedMember : excluded.get(initial)) {
        seen[excludedMember] = true;
      }
    }

    private Optional<List<Integer>> getCycleOfLength(int length) {
      if (inOrder.size() == length) {
        int first = inOrder.get(0);
        int last = inOrder.get(length - 1);
        if (adjacent.get(last).contains(first)) {
          return Optional.of(inOrder);
        }
        return Optional.empty();
      }
      for (int adj : adjacent.get(inOrder.get(inOrder.size() - 1))) {
        if (seen[adj]) {
          continue;
        }
        List<Integer> excl = new ArrayList<>(excluded.get(adj));
        for (int i = excl.size() - 1; i >= 0; i--) {
          int e = excl.get(i);
          if (seen[e]) {
            excl.remove(i);
          } else {
            seen[e] = true;
          }
        }
        seen[adj] = true;
        inOrder.add(adj);
        Optional<List<Integer>> traversal = getCycleOfLength(length);
        if (traversal.isPresent()) {
          return traversal;
        }
        inOrder.remove(inOrder.size() - 1);
        seen[adj] = false;
        for (Integer ex : excl) {
          seen[ex] = false;
        }
      }
      return Optional.empty();
    }
  }
}
